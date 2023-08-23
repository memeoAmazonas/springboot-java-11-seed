package com.pansobao.seed.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Pokemon {

    private String name;
    private Ability ability;
    private Type type;
    private BigDecimal health;
}
