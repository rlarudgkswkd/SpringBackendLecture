package com.codeit.springeventdemo.listener;

import com.codeit.springeventdemo.event.UserRegisteredEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

import java.time.LocalTime;

@Component
public class CacheEventListener {

    @Async("eventTaskExecutor")
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(UserRegisteredEvent event) {

        System.out.println(
                now()
                        + " [CacheEventListener] 캐시 갱신"
        );

        System.out.println(
                now()
                        + " [CacheEventListener] 실행 스레드: "
                        + Thread.currentThread().getName()
        );
    }

    private String now() {
        return "[" + LocalTime.now().withNano(0) + "]";
    }
}