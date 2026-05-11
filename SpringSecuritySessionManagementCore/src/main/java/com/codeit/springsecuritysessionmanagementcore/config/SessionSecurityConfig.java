package com.codeit.springsecuritysessionmanagementcore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SessionSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http
    ) throws Exception {

        http
                // URL 접근 권한 설정
                .authorizeHttpRequests(authz -> authz

                        // 로그인 페이지는 인증 없이 접근 가능
                        .requestMatchers("/login")
                        .permitAll()

                        // 그 외 요청은 인증 필요
                        .anyRequest()
                        .authenticated()
                )

                // 세션 기반 인증 설정
                .sessionManagement(session -> session

                        // 필요한 경우에만 세션 생성
                        .sessionCreationPolicy(
                                SessionCreationPolicy.IF_REQUIRED
                        )
                )

                // 폼 로그인 설정
                .formLogin(form -> form

                        // 사용자 정의 로그인 페이지
                        .loginPage("/login")

                        // 로그인 처리 URL
                        .loginProcessingUrl("/perform-login")

                        // 로그인 성공 시 이동할 URL
                        .defaultSuccessUrl("/dashboard", true)

                        // 로그인 실패 시 이동할 URL
                        .failureUrl("/login?error=true")

                        .permitAll()
                )

                // 로그아웃 설정
                .logout(logout -> logout

                        // 로그아웃 처리 URL
                        .logoutUrl("/logout")

                        // 로그아웃 성공 후 이동할 URL
                        .logoutSuccessUrl("/login?logout=true")

                        // 로그아웃 시 HttpSession 무효화
                        .invalidateHttpSession(true)

                        // 로그아웃 시 JSESSIONID 쿠키 삭제
                        .deleteCookies("JSESSIONID")

                        .permitAll()
                );

        return http.build();
    }
}
