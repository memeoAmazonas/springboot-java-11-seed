package com.pansobao.seed.adapter.rest.pokemon.model.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pansobao.seed.domain.Pokemon;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PokemonModel {
    private  Long id;
    private String name;
    private List<AbilitiesModel> abilities;
    private List<TypesModel> types;

    public Pokemon toPokemonDomain(){
        return Pokemon.builder()
                .name(name)
                .ability(abilities.get(0).toAbilitiesDomain())
                .type(types.get(0).toTypesDomain())
                .build();
    }
}
