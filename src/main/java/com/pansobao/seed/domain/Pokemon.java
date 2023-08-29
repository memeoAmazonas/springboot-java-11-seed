package com.pansobao.seed.domain;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.math.BigDecimal;

@Data
@Builder
@With
public class Pokemon {

    private String name;
    private Ability ability;
    private Type type;
    private BigDecimal health;
}
