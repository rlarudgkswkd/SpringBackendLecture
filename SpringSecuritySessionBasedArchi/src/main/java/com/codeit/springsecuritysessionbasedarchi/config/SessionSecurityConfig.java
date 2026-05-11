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

    private final CustomAuthenticationSuccessHandler
            customAuthenticationSuccessHandler;

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

                .csrf(csrf -> csrf.disable())
            .securityContext(securityContext -> securityContext
                    .securityContextRepository(
                            securityContextRepository()
                    )
            )

            .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/login")
                    .permitAll()

                    .anyRequest()
                    .authenticated()
            )

            .sessionManagement(session -> session

                    .sessionCreationPolicy(
                            SessionCreationPolicy.IF_REQUIRED
                    )

                    .maximumSessions(1)

                    .maxSessionsPreventsLogin(false)

                    .sessionRegistry(sessionRegistry)
            )

            .formLogin(form -> form

                    .loginPage("/login")

                    .loginProcessingUrl(
                            "/perform-login"
                    )

                    .successHandler(
                            customAuthenticationSuccessHandler
                    )

                    .failureUrl(
                            "/login?error=true"
                    )

                    .permitAll()
            )

            .logout(logout -> logout

                    .logoutUrl(
                            "/perform-logout"
                    )

                    .logoutSuccessHandler(
                            customLogoutSuccessHandler
                    )

                    .invalidateHttpSession(true)

                    .clearAuthentication(true)

                    .deleteCookies(
                            "JSESSIONID"
                    )

                    .permitAll()
            );

        return http.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {

        HttpSessionSecurityContextRepository repository =
                new HttpSessionSecurityContextRepository();

        repository.setSpringSecurityContextKey(
                "CUSTOM_SECURITY_CONTEXT"
        );

        repository.setAllowSessionCreation(true);

        return repository;
    }
}