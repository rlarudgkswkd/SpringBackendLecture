package org.example.Head08_SpringBeansUnderstandAndUse.topic03_DependencyInjectionUnderstanding.example04;

import org.springframework.stereotype.Component;

// Bean 이름 지정
@Component("pikachu")  // Bean 이름 명시
public class Pikachu implements Pokemon {
    @Override
    public void attack() {
        System.out.println("피카츄 100만 볼트!");
    }
}
