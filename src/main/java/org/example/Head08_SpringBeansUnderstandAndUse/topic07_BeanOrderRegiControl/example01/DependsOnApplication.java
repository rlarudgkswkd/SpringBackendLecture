package org.example.Head08_SpringBeansUnderstandAndUse.topic07_BeanOrderRegiControl.example01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DependsOnApplication {

    public static void main(String[] args) {
        System.out.println("=== Application 시작 ===");
        ConfigurableApplicationContext context =
                SpringApplication.run(DependsOnApplication.class, args);

        System.out.println("=== 컨텍스트 초기화 완료 ===");

        // Bean 사용 예시 (굳이 안 써도 초기화는 일어남)
        FirstBean first = context.getBean(FirstBean.class);
        SecondBean second = context.getBean(SecondBean.class);

        System.out.println("FirstBean.getData() = " + first.getData());
        System.out.println("SecondBean 사용 완료");

        context.close();
        System.out.println("=== Application 종료 ===");
    }
}