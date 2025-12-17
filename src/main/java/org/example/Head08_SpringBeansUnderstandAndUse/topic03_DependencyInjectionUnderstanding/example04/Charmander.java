package org.example.Head08_SpringBeansUnderstandAndUse.topic03_DependencyInjectionUnderstanding.example04;

import org.springframework.stereotype.Component;

@Component("charmander")
public class Charmander implements Pokemon {
    @Override
    public void attack() {
        System.out.println("파이리: 화염 방사!");
    }
}
