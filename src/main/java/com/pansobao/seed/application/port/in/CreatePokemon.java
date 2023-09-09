package com.pansobao.seed.application.port.in;

import com.pansobao.seed.domain.Pokemon;

public interface CreatePokemon {
    Integer createPokemon(Pokemon pokemon);
}
