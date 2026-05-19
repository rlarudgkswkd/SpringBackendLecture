package com.codeit.springsecurityjwtdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityUserConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

        UserDetails user = User.builder()
                .username("user")
                .password("{noop}1234")
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}1234")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(
                user,
                admin
        );
    }
}