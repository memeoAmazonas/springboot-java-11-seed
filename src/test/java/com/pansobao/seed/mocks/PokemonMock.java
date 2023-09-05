package com.pansobao.seed.mocks;

import com.pansobao.seed.adapter.controller.model.RestResponse;
import com.pansobao.seed.adapter.controller.model.pokemon.AbilityRest;
import com.pansobao.seed.adapter.controller.model.pokemon.PokemonRest;
import com.pansobao.seed.adapter.controller.model.pokemon.TypeRest;
import com.pansobao.seed.domain.Ability;
import com.pansobao.seed.domain.Pokemon;
import com.pansobao.seed.domain.Type;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public final class PokemonMock {
    public static final String STRING_VALUE = "bulbasaur";
    public static final String CREATE_POKEMON = "/api/v1/pokemon/internal/create";

    public static PokemonRest getPokemonRest() {
        return PokemonRest.builder()
                .name(STRING_VALUE)
                .ability(AbilityRest.builder()
                        .name(STRING_VALUE)
                        .description(STRING_VALUE)
                        .build())
                .type(TypeRest.builder()
                        .name(STRING_VALUE)
                        .moveDamageClass(STRING_VALUE)
                        .build())
                .build();
    }
    public static Pokemon getPokemonDomain() {
        return Pokemon.builder()
                .name(STRING_VALUE)
                .ability(Ability.builder()
                        .name(STRING_VALUE)
                        .description(STRING_VALUE)
                        .build())
                .type(Type.builder()
                        .name(STRING_VALUE)
                        .moveDamageClass(STRING_VALUE)
                        .build())
                .build();
    }
    public static RestResponse<Integer> getRestResponseCreatePokemon(){
        return new RestResponse<>(
                "",
                HttpStatus.CREATED.value(),
                CREATE_POKEMON,
       0,
                Collections.emptyMap());
    }

}
