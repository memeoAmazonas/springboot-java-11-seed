package com.pansobao.seed.adapter.rest.pokemon.model.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pansobao.seed.domain.Type;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TypesModel {

    private TypeModel type;
    public Type toTypesDomain(){
        return Type.builder()
                .name(type.getName())
                .build();
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    static class TypeModel{
        private String name;
    }
}
