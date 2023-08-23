package com.pansobao.seed.adapter.controller.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pansobao.seed.domain.Ability;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Objects;

@Value
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AbilityRest {
    String name;
    String description;
    BigDecimal damage;
    public static AbilityRest toAbilityRest(Ability ability){
        return AbilityRest.builder()
                .name(Objects.nonNull(ability.getName()) ? ability.getName() : "")
                .damage(Objects.nonNull(ability.getDamage()) ? ability.getDamage() : null )
                .description(Objects.nonNull(ability.getDescription()) ? ability.getDescription() : "")
                .build();
    }
}
