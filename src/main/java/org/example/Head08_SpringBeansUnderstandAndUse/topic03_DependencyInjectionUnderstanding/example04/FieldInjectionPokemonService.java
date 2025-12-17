package org.example.Head08_SpringBeansUnderstandAndUse.topic03_DependencyInjectionUnderstanding.example04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("pokemonFieldService")
public class FieldInjectionPokemonService {

    // ❌ 필드 주입 (권장 X)
    @Autowired
    @Qualifier("pikachu")
    private Pokemon pokemon;  // 여러 Bean이 존재하면 에러 발생

    public void pokemonAttack() {
        pokemon.attack();
    }
}