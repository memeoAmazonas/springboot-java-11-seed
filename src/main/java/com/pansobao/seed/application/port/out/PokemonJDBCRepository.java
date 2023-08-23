package com.pansobao.seed.application.port.out;

import com.pansobao.seed.domain.Pokemon;

public interface PokemonJDBCRepository {
    Integer CreatePokemon(Pokemon pokemon);

}
