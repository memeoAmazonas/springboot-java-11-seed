package com.pansobao.seed.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class Ability {
    String name;
    String description;
    BigDecimal damage;
}
