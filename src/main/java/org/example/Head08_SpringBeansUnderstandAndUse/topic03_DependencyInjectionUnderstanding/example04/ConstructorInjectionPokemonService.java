package org.example.Head08_SpringBeansUnderstandAndUse.topic03_DependencyInjectionUnderstanding.example04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("pokemonConstructorService")
public class ConstructorInjectionPokemonService {

    private final Pokemon pokemon;

    @Autowired
    public ConstructorInjectionPokemonService(@Qualifier("squirtle") Pokemon pokemon) {
        this.pokemon = pokemon;
        System.out.println("생성자 주입 → 선택된 포켓몬: squirtle");
    }

    public void pokemonAttack() {
        pokemon.attack();
    }
}