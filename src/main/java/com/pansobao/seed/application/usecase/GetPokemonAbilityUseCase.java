package com.pansobao.seed.application.usecase;

import com.pansobao.seed.application.port.in.GetPokemonAbilityQuery;
import com.pansobao.seed.application.port.out.AbilityRepository;
import com.pansobao.seed.application.port.out.PokemonJDBCRepository;
import com.pansobao.seed.application.port.out.PokemonRepository;
import com.pansobao.seed.application.port.out.TypeRepository;
import com.pansobao.seed.config.ErrorCode;
import com.pansobao.seed.domain.Ability;
import com.pansobao.seed.domain.Pokemon;
import com.pansobao.seed.domain.Type;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@Component
@AllArgsConstructor
@Slf4j
public class GetPokemonAbilityUseCase implements GetPokemonAbilityQuery {

    private final PokemonRepository pokemonRepository;
    private final AbilityRepository abilityRepository;

    private final TypeRepository typeRepository;
    private final Executor executor;

    private final PokemonJDBCRepository pokemonJDBCRepository;
    @Override
    public Pokemon getPokemon(String name) throws ExecutionException, InterruptedException {
        Pokemon pokemon = pokemonRepository.getPokemon(name);
        Optional<Pokemon> work = Optional.ofNullable(pokemonJDBCRepository.getPokemonByName("amazonas").orElseThrow(() -> new RuntimeException(ErrorCode.POKEMON_NOT_FOUND.getReason())));
        log.info("-------------- {}",work);

        log.info("Pokemon obtenido [{}]", name);
        CompletableFuture<Ability> abilities = this.getAbility(pokemon.getAbility().getName());
        CompletableFuture<Type> types = this.getType(pokemon.getType().getName());
        CompletableFuture.allOf(
                abilities,
                types
        ).join();
        return Pokemon.builder()
                .ability(abilities.get())
                .type(types.get())
                .name(pokemon.getName())
                .build();
    }

    private CompletableFuture<Ability> getAbility(String name) {
        log.info("Llamando a getAbility name: {}", name);
        return CompletableFuture.supplyAsync(() -> abilityRepository.getAbility(name), executor)
                .exceptionally(ex -> {
                    log.error("Ocurio un error en el llamado asincrono al repositorio ability", ex);
                    return null;
                });
    }

    private CompletableFuture<Type> getType(String name) {
        log.info("Llamando a getType name: {}", name);
        return CompletableFuture.supplyAsync(() -> typeRepository.getType(name), executor)
                .exceptionally(ex -> {
                    log.error("Ocurio un error en el llamado asincrono al repositorio type", ex);
                    return null;
                });
    }
}
