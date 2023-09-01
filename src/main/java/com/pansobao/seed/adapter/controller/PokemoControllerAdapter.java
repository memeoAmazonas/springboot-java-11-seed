package com.pansobao.seed.adapter.controller;

import com.pansobao.seed.adapter.controller.model.PokemonRest;
import com.pansobao.seed.application.port.in.GetPokemonAbilityQuery;
import com.pansobao.seed.domain.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;


@Slf4j
@RestController
@RequestMapping("/api/v1/pokemon")
public final class PokemoControllerAdapter {
    private static final String GET_POKEMON = "/{name}";
    private static final String GET_INTERNAL_POKEMON = "/internal/{name}";
    private final GetPokemonAbilityQuery getPokemonAbilityQuery;

    public PokemoControllerAdapter(GetPokemonAbilityQuery getPokemonAbilityQuery) {
        this.getPokemonAbilityQuery = getPokemonAbilityQuery;
    }

    @GetMapping(GET_POKEMON)
    public PokemonRest getPokemon(@PathVariable("name") String name) throws ExecutionException, InterruptedException {
        log.info("Llamada al servicio pokemon/{}", name);
        Pokemon pokemon = this.getPokemonAbilityQuery.getPokemon(name);
        PokemonRest response = PokemonRest.toPokemonRest(pokemon);
        log.info("Respuesta del servicio pokemon/{}: [{}]", name, response);
        return response;
    }
    @GetMapping(GET_INTERNAL_POKEMON)
    public PokemonRest getInternalPokemon(@PathVariable("name") String name){
        log.info("Llamada al servicio pokemon/internal/{}", name);
        Pokemon pokemon = this.getPokemonAbilityQuery.getInternal(name);
        PokemonRest response = PokemonRest.toPokemonRest(pokemon);
        log.info("Respuesta del servicio pokemon/internal/{} : [{}]", name, response);
        return response;
    }
}
