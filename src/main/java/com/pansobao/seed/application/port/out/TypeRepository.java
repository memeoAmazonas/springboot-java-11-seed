package com.pansobao.seed.application.port.out;

import com.pansobao.seed.domain.Ability;
import com.pansobao.seed.domain.Type;

public interface TypeRepository {
    Type getType(String name);
}
