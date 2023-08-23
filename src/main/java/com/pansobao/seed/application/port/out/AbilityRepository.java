package com.pansobao.seed.application.port.out;

import com.pansobao.seed.domain.Ability;

public interface AbilityRepository {
    Ability getAbility(String name);
}
