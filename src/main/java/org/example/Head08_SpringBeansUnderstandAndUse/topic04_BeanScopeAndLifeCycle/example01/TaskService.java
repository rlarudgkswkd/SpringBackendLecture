package org.example.Head08_SpringBeansUnderstandAndUse.topic04_BeanScopeAndLifeCycle.example01;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final ApplicationContext applicationContext;

    public TaskService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void executeTasks() {
        System.out.println("[TaskService] 새로운 TaskProcessor 두 개 요청");

        // prototype Bean은 getBean() 할 때마다 새 인스턴스
        TaskProcessor processor1 = applicationContext.getBean(TaskProcessor.class);
        TaskProcessor processor2 = applicationContext.getBean(TaskProcessor.class);

        System.out.println("Processor1 ID: " + processor1.getTaskId());
        System.out.println("Processor2 ID: " + processor2.getTaskId());
        System.out.println("같은 인스턴스인가? " + (processor1 == processor2));

        processor1.processTask("첫 번째 작업");
        processor2.processTask("두 번째 작업");
    }
}