package com.pansobao.seed.adapter.controller.model.pokemon;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pansobao.seed.domain.Ability;
import com.pansobao.seed.domain.Pokemon;
import lombok.Builder;
import lombok.Data;

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
    public Pokemon toDomain(){
        return Pokemon.builder()
                .name(this.name)
                .ability(Ability.builder()
                        .name(this.getAbility().getName())
                        .build())
                .build();
    }
}
