//package com.codeit.springsecuritysessionbasedarchi.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//import org.springframework.security.config.http.SessionCreationPolicy;
//
//import org.springframework.security.web.SecurityFilterChain;
//
//import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
//import org.springframework.security.web.context.SecurityContextRepository;
//
//@Configuration
//@EnableWebSecurity
//public class SessionSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .headers(headers -> headers
//                        .cacheControl(cache -> {})
//                )
//
//                .securityContext(securityContext -> securityContext
//                        .securityContextRepository(
//                                securityContextRepository()
//                        )
//                )
//
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/login", "/public/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//
//                // 1. 세션 기반 인증 설정
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                        .maximumSessions(1)
//                        .maxSessionsPreventsLogin(false)
//                )
//
//                // 2. 폼 로그인 설정
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .loginProcessingUrl("/perform-login")
//                        .defaultSuccessUrl("/dashboard", true)
//                        .failureUrl("/login?error=true")
//                        .permitAll()
//                )
//
//                // 3. 로그아웃 설정
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout=true")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                );
//
//        return http.build();
//    }
//
//    // 4. SecurityContext 저장소 설정
//    @Bean
//    public SecurityContextRepository securityContextRepository() {
//
//        HttpSessionSecurityContextRepository repository =
//                new HttpSessionSecurityContextRepository();
//
//        // SecurityContext 속성 이름 커스터마이징
//        repository.setSpringSecurityContextKey(
//                "CUSTOM_SECURITY_CONTEXT"
//        );
//
//        // 세션 생성 허용
//        repository.setAllowSessionCreation(true);
//
//        return repository;
//    }
//}
package com.codeit.springsecuritysessionbasedarchi.config;

import com.codeit.springsecuritysessionbasedarchi.handler.CustomAuthenticationSuccessHandler;
import com.codeit.springsecuritysessionbasedarchi.handler.CustomLogoutSuccessHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.session.SessionRegistry;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@EnableWebSecurity
public class SessionSecurityConfig {

    // 로그인 성공 이후 동작 처리 핸들러
    //
    // 예:
    // - 세션 정보 저장
    // - AJAX 응답 처리
    // - Redirect 처리
    private final CustomAuthenticationSuccessHandler
            customAuthenticationSuccessHandler;

    // 로그아웃 성공 이후 동작 처리 핸들러
    //
    // 예:
    // - 세션 만료 처리
    // - SessionRegistry 관리
    // - Redirect 처리
    private final CustomLogoutSuccessHandler
            customLogoutSuccessHandler;

    public SessionSecurityConfig(
            CustomAuthenticationSuccessHandler
                    customAuthenticationSuccessHandler,

            CustomLogoutSuccessHandler
                    customLogoutSuccessHandler
    ) {

        this.customAuthenticationSuccessHandler =
                customAuthenticationSuccessHandler;

        this.customLogoutSuccessHandler =
                customLogoutSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            SessionRegistry sessionRegistry
    ) throws Exception {

        http

                // 실습 편의를 위해 CSRF 비활성화
                //
                // fetch AJAX 테스트를 쉽게 수행하기 위한 설정이다.
                //
                // 실무에서는 일반적으로 활성화 상태로 사용한다.
                .csrf(csrf -> csrf.disable())

                // SecurityContext 저장소 설정
                //
                // Authentication 객체가 저장된
                // SecurityContext를 HttpSession에 저장한다.
                .securityContext(securityContext -> securityContext
                        .securityContextRepository(
                                securityContextRepository()
                        )
                )

                // URL 접근 권한 설정
                .authorizeHttpRequests(authz -> authz

                        // 로그인 페이지는 누구나 접근 가능
                        .requestMatchers("/login")
                        .permitAll()

                        // 그 외 모든 요청은 인증 필요
                        .anyRequest()
                        .authenticated()
                )

                // 세션 기반 인증 정책 설정
                .sessionManagement(session -> session

                        // 세션이 필요한 경우에만 생성
                        //
                        // 인증 성공 시 HttpSession 생성
                        .sessionCreationPolicy(
                                SessionCreationPolicy.IF_REQUIRED
                        )

                        // 최대 동시 로그인 세션 수 제한
                        .maximumSessions(1)

                        // false:
                        // 새로운 로그인 발생 시
                        // 기존 세션 만료
                        .maxSessionsPreventsLogin(false)

                        // SessionRegistry 사용
                        //
                        // 현재 활성 세션 추적 가능
                        .sessionRegistry(sessionRegistry)
                )

                // 폼 로그인 설정
                .formLogin(form -> form

                        // 사용자 정의 로그인 페이지
                        .loginPage("/login")

                        // 로그인 처리 URL
                        //
                        // POST 요청으로 인증 수행
                        .loginProcessingUrl(
                                "/perform-login"
                        )

                        // 로그인 성공 이후 처리 핸들러
                        .successHandler(
                                customAuthenticationSuccessHandler
                        )

                        // 로그인 실패 시 이동 URL
                        .failureUrl(
                                "/login?error=true"
                        )

                        .permitAll()
                )

                // 로그아웃 설정
                .logout(logout -> logout

                        // 로그아웃 처리 URL
                        .logoutUrl(
                                "/perform-logout"
                        )

                        // 로그아웃 성공 이후 처리 핸들러
                        .logoutSuccessHandler(
                                customLogoutSuccessHandler
                        )

                        // HttpSession 제거
                        .invalidateHttpSession(true)

                        // Authentication 제거
                        .clearAuthentication(true)

                        // 브라우저 JSESSIONID 쿠키 제거
                        .deleteCookies(
                                "JSESSIONID"
                        )

                        .permitAll()
                );

        return http.build();
    }

    // SecurityContext 저장소 Bean
    //
    // Spring Security는 Authentication 객체를
    // SecurityContext 내부에 저장한다.
    //
    // 그리고 SecurityContext는 HttpSession에 저장된다.
    @Bean
    public SecurityContextRepository securityContextRepository() {

        HttpSessionSecurityContextRepository repository =
                new HttpSessionSecurityContextRepository();

        // HttpSession 내부 저장 키 이름 변경
        //
        // 기본값:
        // SPRING_SECURITY_CONTEXT
        //
        // 현재 실습:
        // CUSTOM_SECURITY_CONTEXT
        repository.setSpringSecurityContextKey(
                "CUSTOM_SECURITY_CONTEXT"
        );

        // 필요 시 세션 생성 허용
        repository.setAllowSessionCreation(true);

        return repository;
    }
}