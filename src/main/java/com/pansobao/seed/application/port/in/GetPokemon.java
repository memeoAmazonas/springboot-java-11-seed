package com.pansobao.seed.application.port.in;

import com.pansobao.seed.domain.Pokemon;

import java.util.concurrent.ExecutionException;

public interface GetPokemon {

    Pokemon getPokemon(String name) throws ExecutionException, InterruptedException;
    Pokemon getInternal(String name);


}
