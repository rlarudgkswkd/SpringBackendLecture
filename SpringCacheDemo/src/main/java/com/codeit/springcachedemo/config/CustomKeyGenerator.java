package com.codeit.springcachedemo.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("customKeyGenerator")
public class CustomKeyGenerator
        implements KeyGenerator {

    @Override
    public Object generate(
            Object target,
            Method method,
            Object... params
    ) {

        return target.getClass().getSimpleName()
                + "_"
                + method.getName()
                + "_"
                + params[0];
    }
}