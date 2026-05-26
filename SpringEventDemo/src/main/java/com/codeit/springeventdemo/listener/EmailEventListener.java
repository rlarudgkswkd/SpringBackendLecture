package com.codeit.springeventdemo.listener;

import com.codeit.springeventdemo.event.UserRegisteredEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

import java.time.LocalTime;

@Component
public class EmailEventListener {

    @Async("eventTaskExecutor")
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(UserRegisteredEvent event) {

        System.out.println(
                now()
                        + " [EmailEventListener] 이메일 발송 시작"
        );

        System.out.println(
                now()
                        + " [EmailEventListener] 실행 스레드: "
                        + Thread.currentThread().getName()
        );

        sleep(2000);

        System.out.println(
                now()
                        + " [EmailEventListener] 이메일 발송 완료"
        );
    }

    private void sleep(long millis) {

        try {

            Thread.sleep(millis);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }

    private String now() {
        return "[" + LocalTime.now().withNano(0) + "]";
    }
}