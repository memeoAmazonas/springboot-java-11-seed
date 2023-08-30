package com.pansobao.seed.adapter.controller.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pansobao.seed.domain.Pokemon;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.Objects;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PokemonRest {
    String name;
    AbilityRest ability;
    TypeRest type;

    public static PokemonRest toPokemonRest(Pokemon pokemon) {
        return Objects.nonNull(pokemon) ?
                PokemonRest.builder()
                        .name(pokemon.getName())
                        .ability(AbilityRest.toAbilityRest(pokemon.getAbility()))
                        .type(TypeRest.toTypeRest(pokemon.getType()))
                        .build() : null;
    }
}
