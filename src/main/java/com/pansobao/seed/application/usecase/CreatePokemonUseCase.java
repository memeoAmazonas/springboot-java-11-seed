package com.pansobao.seed.application.usecase;

import com.pansobao.seed.application.port.in.CreatePokemon;
import com.pansobao.seed.application.port.out.PokemonJDBCRepository;
import com.pansobao.seed.domain.Pokemon;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class CreatePokemonUseCase implements CreatePokemon {
    private final PokemonJDBCRepository pokemonJDBCRepository;
    @Override
    public Integer createPokemon(Pokemon pokemon) {
        return pokemonJDBCRepository.createPokemon(pokemon);
    }
}
