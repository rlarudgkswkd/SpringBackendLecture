package com.codeit.springsecurityadvancedsession.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        /*
                         * 로그인 페이지 허용
                         */
                        .requestMatchers("/login")
                        .permitAll()

                        /*
                         * USER 권한 필요
                         */
                        .requestMatchers("/user/**")
                        .hasRole("USER")

                        /*
                         * ADMIN 권한 필요
                         */
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        /*
                         * 나머지 요청은 인증 필요
                         */
                        .anyRequest()
                        .authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")

                        .defaultSuccessUrl(
                                "/home",
                                true
                        )

                        .permitAll()
                )

                /*
                 * Remember-Me 설정
                 */
                .rememberMe(remember -> remember

                        /*
                         * Remember-Me 쿠키 암호화 키
                         */
                        .key("remember-me-secret-key")

                        /*
                         * 쿠키 유지 시간
                         *
                         * 60 * 60 * 24 * 14
                         * = 14일
                         */
                        .tokenValiditySeconds(
                                1209600
                        )

                        /*
                         * Remember-Me 파라미터 이름
                         *
                         * login.html checkbox name과 일치해야 한다.
                         */
                        .rememberMeParameter(
                                "remember-me"
                        )

                        /*
                         * Remember-Me 쿠키 이름
                         */
                        .rememberMeCookieName(
                                "remember-me-cookie"
                        )
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder
    ) {

        UserDetails user =
                User.builder()
                        .username("user")
                        .password(
                                passwordEncoder.encode("1234")
                        )
                        .roles("USER")
                        .build();

        UserDetails admin =
                User.builder()
                        .username("admin")
                        .password(
                                passwordEncoder.encode("1234")
                        )
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(
                user,
                admin
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
