package org.example.Head08_SpringBeansUnderstandAndUse.topic03_DependencyInjectionUnderstanding.example04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("pokemonMultiService")
public class MultiQualifierPokemonService {

    private final Pokemon electric;
    private final Pokemon fire;
    private final Pokemon water;

    @Autowired
    public MultiQualifierPokemonService(
            @Qualifier("pikachu") Pokemon electric,
            @Qualifier("charmander") Pokemon fire,
            @Qualifier("squirtle") Pokemon water
    ) {
        this.electric = electric;
        this.fire = fire;
        this.water = water;

        System.out.println("여러 포켓몬 Bean 주입 완료");
    }

    public void pokemonAttack() {
        electric.attack();
        fire.attack();
        water.attack();
    }
}