package org.example.Head08_SpringBeansUnderstandAndUse.topic03_DependencyInjectionUnderstanding.example04;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("\n=== 실행 테스트 ===");

        // ① 필드 주입 방식 (권장 X)
        // FieldInjectionPokemonService service =
        //         context.getBean("pokemonFieldService", FieldInjectionPokemonService.class);

        // ② 생성자 주입 방식 (권장)
        // ConstructorInjectionPokemonService service =
        //         context.getBean("pokemonConstructorService", ConstructorInjectionPokemonService.class);

        // ③ 여러 Bean 동시에 주입하기
        MultiQualifierPokemonService service =
                context.getBean("pokemonMultiService", MultiQualifierPokemonService.class);

        service.pokemonAttack();
        context.close();
    }
}