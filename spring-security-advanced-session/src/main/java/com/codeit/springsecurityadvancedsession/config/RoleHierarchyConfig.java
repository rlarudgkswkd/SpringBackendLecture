package com.codeit.springsecurityadvancedsession.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

@Configuration
public class RoleHierarchyConfig {

    @Bean
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl hierarchy =
                new RoleHierarchyImpl();

        /*
         * 권한 계층 구조 정의
         *
         * ROLE_ADMIN 은
         * ROLE_USER 권한을 포함한다.
         */
        hierarchy.setHierarchy("""
                ROLE_ADMIN > ROLE_USER
                """);

        return hierarchy;
    }
}
