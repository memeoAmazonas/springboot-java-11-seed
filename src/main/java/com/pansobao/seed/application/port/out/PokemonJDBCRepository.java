package com.pansobao.seed.application.port.out;

import com.pansobao.seed.domain.Pokemon;

import java.util.Optional;

public interface PokemonJDBCRepository {
    Integer createPokemon(Pokemon pokemon);
    Pokemon getPokemonByName(String name);
}
