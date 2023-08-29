package com.pansobao.seed.adapter.jdbc.postgres.model;

import com.pansobao.seed.domain.Ability;
import com.pansobao.seed.domain.Pokemon;
import com.pansobao.seed.domain.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PokemonJDBCModel implements Serializable {
    private String name;
    private String ability;
    private Integer id;

    public static Pokemon toDomain(PokemonJDBCModel model) {
        return Pokemon.builder()
                .name(model.getName())
                .ability(Ability.builder()
                        .name(model.ability)
                        .build())
                .type(Type.builder()
                        .name(model.getName().concat(model.getAbility().toUpperCase()))
                        .build())
                .build();
    }
}
