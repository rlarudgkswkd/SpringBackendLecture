package org.example.Head08_SpringBeansUnderstandAndUse.topic03_DependencyInjectionUnderstanding.example04;

import org.springframework.stereotype.Component;

@Component("squirtle")
public class Squirtle implements Pokemon {
    @Override
    public void attack() {
        System.out.println("꼬부기 거품광선!");
    }
}
