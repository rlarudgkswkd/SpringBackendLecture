package com.codeit.springeventdemo.listener;

import com.codeit.springeventdemo.event.UserRegisteredEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

import java.time.LocalTime;

@Component
public class ActivityLogEventListener {

    @Async("eventTaskExecutor")
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(UserRegisteredEvent event) {

        System.out.println(
                now()
                        + " [ActivityLogEventListener] 활동 로그 저장"
        );

        System.out.println(
                now()
                        + " [ActivityLogEventListener] 실행 스레드: "
                        + Thread.currentThread().getName()
        );
    }

    private String now() {
        return "[" + LocalTime.now().withNano(0) + "]";
    }
}