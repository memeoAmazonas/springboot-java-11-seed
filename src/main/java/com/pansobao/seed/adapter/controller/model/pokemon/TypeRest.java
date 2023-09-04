package com.pansobao.seed.adapter.controller.model.pokemon;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pansobao.seed.domain.Type;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TypeRest{
    String name;
    String moveDamageClass;
    public static TypeRest toTypeRest(Type type){
        return TypeRest.builder()
                .name(type.getName())
                .moveDamageClass(type.getMoveDamageClass())
                .build();
    }
}
