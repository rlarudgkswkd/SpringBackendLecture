package com.codeit.springasyncexceptiondemo.handler;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.Arrays;

@Component
public class CustomAsyncExceptionHandler
        implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(
            Throwable ex,
            Method method,
            Object... params
    ) {

        System.out.println();
        System.out.println(
                now() + " [GlobalAsyncExceptionHandler] 예외 발생"
        );

        System.out.println(
                now() + " [GlobalAsyncExceptionHandler] 메서드: "
                        + method.getName()
        );

        System.out.println(
                now() + " [GlobalAsyncExceptionHandler] 파라미터: "
                        + Arrays.toString(params)
        );

        System.out.println(
                now() + " [GlobalAsyncExceptionHandler] 예외 타입: "
                        + ex.getClass().getSimpleName()
        );

        System.out.println(
                now() + " [GlobalAsyncExceptionHandler] 예외 메시지: "
                        + ex.getMessage()
        );

        System.out.println();
    }

    private String now() {

        return "[" + LocalTime.now().withNano(0) + "]";
    }
}