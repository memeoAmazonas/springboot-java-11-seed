package com.pansobao.seed.adapter.rest.pokemon.model.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pansobao.seed.domain.Type;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TypeModel {

    private String name;
    private MoveDamageModel moveDamageClass;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    static class MoveDamageModel {
        private String name;
    }

    public Type toTypeDomain() {
        return Type.builder()
                .name(name)
                .moveDamageClass(moveDamageClass.getName())
                .build();
    }

}