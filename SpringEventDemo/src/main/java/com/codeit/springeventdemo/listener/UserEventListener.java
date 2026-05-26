package com.codeit.springeventdemo.listener;

import com.codeit.springeventdemo.event.UserRegisteredEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

import java.time.LocalTime;

@Component
public class UserEventListener {

    @Async("eventTaskExecutor")
    @EventListener
    public void asyncEventListener(
            UserRegisteredEvent event
    ) {

        System.out.println(
                now() + " [AsyncEventListener] 비동기 이벤트 처리"
        );

        System.out.println(
                now() + " [AsyncEventListener] 실행 스레드: "
                        + Thread.currentThread().getName()
        );

        sleep(2000);

        System.out.println(
                now() + " [AsyncEventListener] 처리 완료"
        );
    }

    @TransactionalEventListener(
            phase = TransactionPhase.BEFORE_COMMIT
    )
    public void beforeCommit(
            UserRegisteredEvent event
    ) {

        System.out.println(
                now() + " [BEFORE_COMMIT] 커밋 직전 실행"
        );
    }

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void afterCommit(
            UserRegisteredEvent event
    ) {

        System.out.println(
                now() + " [AFTER_COMMIT] 커밋 성공 후 실행"
        );
    }

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_ROLLBACK
    )
    public void afterRollback(
            UserRegisteredEvent event
    ) {

        System.out.println(
                now() + " [AFTER_ROLLBACK] 롤백 후 실행"
        );
    }

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMPLETION
    )
    public void afterCompletion(
            UserRegisteredEvent event
    ) {

        System.out.println(
                now() + " [AFTER_COMPLETION] 트랜잭션 종료 후 실행"
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