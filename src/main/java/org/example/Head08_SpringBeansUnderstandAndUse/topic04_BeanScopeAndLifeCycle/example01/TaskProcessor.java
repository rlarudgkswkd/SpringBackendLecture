package org.example.Head08_SpringBeansUnderstandAndUse.topic04_BeanScopeAndLifeCycle.example01;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Scope("prototype")  // 요청할 때마다 새 인스턴스 생성
public class TaskProcessor {

    private final String taskId = UUID.randomUUID().toString();
    private final LocalDateTime createdAt = LocalDateTime.now();
    private TaskStatus status = TaskStatus.CREATED;

    public void processTask(String data) {
        status = TaskStatus.PROCESSING;
        System.out.println("[TaskProcessor] 처리 중인 Task ID: " + taskId +
                ", createdAt=" + createdAt + ", data=" + data);
        status = TaskStatus.COMPLETED;
    }

    public String getTaskId() {
        return taskId;
    }

    public TaskStatus getStatus() {
        return status;
    }
}