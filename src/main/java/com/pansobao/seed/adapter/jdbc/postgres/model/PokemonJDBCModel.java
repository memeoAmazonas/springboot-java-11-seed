package com.pansobao.seed.adapter.jdbc.postgres.model;

import com.pansobao.seed.domain.Ability;
import com.pansobao.seed.domain.Pokemon;
import com.pansobao.seed.domain.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonJDBCModel implements Serializable {
    private String name;
    private String ability;
    private Integer id;

    public Pokemon toDomain() {
        return Pokemon.builder()
                .name(this.name)
                .ability(Ability.builder()
                        .name(this.name)
                        .build())
                .type(Type.builder()
                        .name(this.name.concat(this.ability.toUpperCase()))
                        .build())
                .build();
    }

}
