package com.pansobao.seed.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Type {
    String name;
    String moveDamageClass;
}
