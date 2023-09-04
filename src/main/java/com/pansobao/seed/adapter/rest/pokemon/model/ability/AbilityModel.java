package com.pansobao.seed.adapter.rest.pokemon.model.ability;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pansobao.seed.domain.Ability;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AbilityModel{
    private String name;
    @JsonProperty("effect_entries")
    private List<EffectsEntriesModel> effectsEntries;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    static class EffectsEntriesModel{
        @JsonProperty
        private String effect;
    }
    public Ability toAbilityDomain(){
        return Ability.builder()
                .name(name)
                .description(effectsEntries.get(0).getEffect())
                .build();
    }
}