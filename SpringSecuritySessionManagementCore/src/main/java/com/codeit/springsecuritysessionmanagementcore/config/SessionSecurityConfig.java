package com.codeit.springsecuritysessionmanagementcore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@EnableWebSecurity
public class SessionSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http
    ) throws Exception {

        http

                /*
                 * SecurityContext 저장소 설정
                 *
                 * SecurityContext를
                 * HttpSession에 저장한다.
                 */
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
                )

                .formLogin(form -> form

                        .loginPage("/login")

                        .loginProcessingUrl("/perform-login")

                        .defaultSuccessUrl(
                                "/dashboard",
                                true
                        )

                        .failureUrl(
                                "/login?error=true"
                        )

                        .permitAll()
                )

                .logout(logout -> logout

                        .logoutUrl("/logout")

                        .logoutSuccessUrl(
                                "/login?logout=true"
                        )

                        .invalidateHttpSession(true)

                        .deleteCookies("JSESSIONID")

                        .permitAll()
                );

        return http.build();
    }

    /*
     * SecurityContext 저장소 Bean
     */
    @Bean
    public SecurityContextRepository securityContextRepository() {

        HttpSessionSecurityContextRepository repository =
                new HttpSessionSecurityContextRepository();

        /*
         * HttpSession 내부에 저장될 키 이름 변경
         *
         * 기본값:
         * SPRING_SECURITY_CONTEXT
         */
        repository.setSpringSecurityContextKey(
                "CUSTOM_SECURITY_CONTEXT"
        );

        /*
         * 인증 시 세션 생성 허용
         */
        repository.setAllowSessionCreation(true);

        return repository;
    }
}