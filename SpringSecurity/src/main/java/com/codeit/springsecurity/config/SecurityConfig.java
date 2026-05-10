package com.codeit.springsecurity.config;

import com.codeit.springsecurity.customfilter.AuditLoggingFilter;
import com.codeit.springsecurity.customfilter.JwtLoggingFilter;
import com.codeit.springsecurity.customfilter.PerformanceMonitoringFilter;
import com.codeit.springsecurity.customfilter.RequestLoggingFilter;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                // [기존 Spring Security 소개와 설정 실습용]
//                .formLogin(Customizer.withDefaults());

                // [필터 아키텍처의 이해 교안 실습용]
//                .formLogin(Customizer.withDefaults())
//
//                // UsernamePasswordAuthenticationFilter가 실행되기 전에
//                // JwtLoggingFilter를 먼저 실행하도록 필터 체인에 추가
//                .addFilterBefore(
//                        new JwtLoggingFilter(),
//                        //그러나 현재 로그인을 안하는 /public으로 접근하고 있기에 해당 필터는 실습에서 동작 안함.
//                        UsernamePasswordAuthenticationFilter.class
//                );

                // [
                .formLogin(Customizer.withDefaults())

                // 가장 먼저 실행
                .addFilterBefore(
                        new RequestLoggingFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )

                // RequestLoggingFilter 이후 실행
                // UsernamePasswordAuthenticationFilter 이전 실행
                .addFilterBefore(
                        new JwtLoggingFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )

                // JwtLoggingFilter 이후 실행
                // UsernamePasswordAuthenticationFilter 이후 실행
                .addFilterAfter(
                        new AuditLoggingFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )

                // AuthorizationFilter 이후 실행
                .addFilterAfter(
                        new PerformanceMonitoringFilter(),
                        AuthorizationFilter.class
                );

        return http.build();
    }

    @Bean
    public CommandLineRunner printFilters(
            @Qualifier("filterChain")
            SecurityFilterChain securityFilterChain
    ) {

        return args -> {

            System.out.println();
            System.out.println("===== Spring Security Filter 목록 =====");

            List<Filter> filters = securityFilterChain.getFilters();

            for (int i = 0; i < filters.size(); i++) {

                System.out.println(
                        (i + 1) + ". "
                                + filters.get(i)
                                .getClass()
                                .getSimpleName()
                );
            }
        };
    }
}