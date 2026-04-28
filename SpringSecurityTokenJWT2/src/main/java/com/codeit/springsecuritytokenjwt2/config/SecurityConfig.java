package com.codeit.springsecuritytokenjwt2.config;

import com.codeit.springsecuritytokenjwt2.auth.handler.CustomAccessDeniedHandler;
import com.codeit.springsecuritytokenjwt2.auth.handler.LoginFailureHandler;
import com.codeit.springsecuritytokenjwt2.jwt.JwtAuthenticationFilter;
import com.codeit.springsecuritytokenjwt2.jwt.JwtLoginSuccessHandler;
import com.codeit.springsecuritytokenjwt2.jwt.JwtLogoutHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Spring Security 설정
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /* 설명. 사용자의 비밀번호를 BCrypt 암호화하기 위한 Bean 설정 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	/* 설명. 필터 체인 디버깅을 위한 Bean 설정 */
    @Bean
    public CommandLineRunner debugFilterChain(SecurityFilterChain filterChain) {

        return args -> {
            int filterSize = filterChain.getFilters().size();
           
			List<String> filterNames = IntStream.range(0, filterSize)
                    .mapToObj(idx -> String.format("\t[%s/%s] %s", idx + 1, filterSize,
                            filterChain.getFilters().get(idx).getClass()))
                    .toList();
            
			System.out.println("현재 적용된 필터 체인 목록:");
            filterNames.forEach(System.out::println);
        };
    }

    /* 설명. DaoAuthenticationProvider Bean 설정 */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        
		return authProvider;
    }

    /* 설명. AuthenticationManager Bean 설정 */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /* 설명. SecurityFilterChain 설정 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           DaoAuthenticationProvider authenticationProvider,
                                           JwtAuthenticationFilter jwtAuthenticationFilter,
                                           JwtLoginSuccessHandler jwtLoginSuccessHandler,
                                           JwtLogoutHandler jwtLogoutHandler,
                                           LoginFailureHandler loginFailureHandler,
                                           CustomAccessDeniedHandler customAccessDeniedHandler) throws Exception {
        System.out.println("[SecurityConfig] FilterChain 구성 시작 - JWT 기반");
        
        http
                // CSRF 설정 - 쿠키 기반 CSRF 토큰 사용
                .csrf(csrf -> csrf
						// 쿠키 기반 CSRF 토큰 사용
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
						// CSRF 토큰 요청 처리 핸들러 설정
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                )
                // 요청 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 메인 페이지 및 개발 도구는 인증 불필요 (정적 리소스는 webSecurityCustomizer에서 처리)
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()

                        // 인증 없이 접근 가능한 API
                        .requestMatchers("/api/auth/csrf-token").permitAll()            // CSRF 토큰 발급
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()     // 회원가입
                        .requestMatchers("/api/auth/login").permitAll()                 // 로그인
                        .requestMatchers(HttpMethod.POST, "/api/auth/refresh").permitAll() // 토큰 재발급
                        .requestMatchers("/api/auth/logout").permitAll()                // 로그아웃
                        .requestMatchers(HttpMethod.GET, "/api/menus").permitAll()      // 메뉴 전체 조회
                        .requestMatchers(HttpMethod.GET, "/api/images/**").permitAll()  // 이미지 파일 조회

                        // 메뉴 상세 조회는 인증 필요
                        .requestMatchers(HttpMethod.GET, "/api/menus/*").authenticated()

                        // 메뉴 관리는 ADMIN 권한 필요
                        .requestMatchers(HttpMethod.POST, "/api/menus").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/menus/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/menus/*").hasRole("ADMIN")

                        // 사용자 권한 변경은 ADMIN 권한 필요
                        .requestMatchers(HttpMethod.PUT, "/api/auth/role").hasRole("ADMIN")

                        // 그 외 모든 API는 인증 필요
                        .requestMatchers("/api/**").authenticated()

                        // 그 외 모든 요청은 허용
                        .anyRequest().permitAll()
                )

                // 무상태 세션 정책
                .sessionManagement(session -> session
						// JWT 기반 인증에서는 세션이 불필요하므로 STATELESS로 설정 (참고로 디폴트 설정은 IF_REQUIRED)
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Form 기반 로그인 활성화
                .formLogin(form -> form
                        // 로그인 처리 URL
						.loginProcessingUrl("/api/auth/login")
                        // 로그인 성공 시 처리 핸들러
                        .successHandler(jwtLoginSuccessHandler)
                        // 로그인 실패 시 처리 핸들러
                        .failureHandler(loginFailureHandler)
						// 로그인 페이지는 인증 없이 접근 가능
                        .permitAll()
                )
				// HTTP Basic 인증 비활성화
                .httpBasic(basic -> basic.disable())

				// 로그아웃 설정
                .logout(logout -> logout
						// 로그아웃 처리 URL
						.logoutUrl("/api/auth/logout")
						// 로그아웃 처리 핸들러
                        .addLogoutHandler(jwtLogoutHandler)
						// 로그아웃 성공 시 처리 핸들러
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
						// 로그아웃 페이지는 인증 없이 접근 가능
                        .permitAll()
                )

                // 예외 처리 설정
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                        // 커스텀 핸들러(403 에러 핸들러)로 권한 없음 예외 처리
                        .accessDeniedHandler(customAccessDeniedHandler)
                )

                // 인증 프로바이더 설정 (앞서 Bean으로 정의한 DaoAuthenticationProvider 사용)
                .authenticationProvider(authenticationProvider)
                
				/* 설명. JWT 인증 필터를 UsernamePasswordAuthenticationFilter 이전에 배치한다.
				 *  이렇게 배치하는 이유는 요청에 Authorization 헤더가 있을 경우 JWT 토큰으로 먼저 인증을 시도하고,
				 *  JWT 인증이 성공하면 SecurityContext에 인증 정보를 설정하여 후속 필터들이 이미 인증된 상태로 처리되도록 하기 위함이다.
				 *  만약 JWT 토큰이 없거나 유효하지 않다면 UsernamePasswordAuthenticationFilter가 폼 로그인을 처리할 수 있다.
				 * */
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        System.out.println("[SecurityConfig] FilterChain 구성 완료");

        return http.build();
    }

    /* 설명. WebSecurity 설정 - 정적 리소스는 Spring Security 필터 체인에서 완전히 제외 */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                // 브라우저 기본 요청 및 에러 페이지
                .requestMatchers("/favicon.ico", "/error")
                // 정적 리소스 (CSS, JavaScript, 이미지 등)
                .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**");
    }

    /* 설명. RoleHierarchy Bean 설정 */
    @Bean
    public RoleHierarchy roleHierarchy() {

        RoleHierarchy hierarchy = RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_USER");
        System.out.println("[SecurityConfig] RoleHierarchy 설정 완료: ROLE_ADMIN > ROLE_USER");

        return hierarchy;
    }

    /* 설명. Method Security에서 RoleHierarchy를 사용하기 위한 설정 */
    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(
            RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);
        System.out.println("[SecurityConfig] MethodSecurityExpressionHandler 설정 완료");
        return handler;
    }

}