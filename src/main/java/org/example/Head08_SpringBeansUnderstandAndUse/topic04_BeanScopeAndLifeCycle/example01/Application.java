package org.example.Head08_SpringBeansUnderstandAndUse.topic04_BeanScopeAndLifeCycle.example01;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("=== 1. 싱글톤 Bean(UserService) 테스트 ===");
        UserService userService1 = context.getBean(UserService.class);
        UserService userService2 = context.getBean(UserService.class);

        System.out.println("userService1 = " + userService1);
        System.out.println("userService2 = " + userService2);
        System.out.println("같은 인스턴스인가? " + (userService1 == userService2));

        System.out.println();
        System.out.println("=== 2. 프로토타입 Bean(TaskProcessor) 직접 요청 테스트 ===");
        TaskProcessor tp1 = context.getBean(TaskProcessor.class);
        TaskProcessor tp2 = context.getBean(TaskProcessor.class);

        System.out.println("tp1 ID = " + tp1.getTaskId());
        System.out.println("tp2 ID = " + tp2.getTaskId());
        System.out.println("같은 인스턴스인가? " + (tp1 == tp2));

        System.out.println();
        System.out.println("=== 3. TaskService에서 prototype Bean 사용 ===");
        TaskService taskService = context.getBean(TaskService.class);
        taskService.executeTasks();

        context.close();
    }
}