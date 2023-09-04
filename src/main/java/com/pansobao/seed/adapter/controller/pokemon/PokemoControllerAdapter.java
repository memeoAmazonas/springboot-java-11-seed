package com.pansobao.seed.adapter.controller.pokemon;

import com.pansobao.seed.adapter.controller.model.pokemon.PokemonRest;
import com.pansobao.seed.adapter.controller.model.RestResponse;
import com.pansobao.seed.adapter.controller.processor.Processor;
import com.pansobao.seed.adapter.controller.processor.RequestProcessor;
import com.pansobao.seed.application.port.in.CreatePokemon;
import com.pansobao.seed.application.port.in.GetPokemon;
import com.pansobao.seed.domain.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.ExecutionException;


@Slf4j
@RestController
@RequestMapping("/api/v1/pokemon")
public final class PokemoControllerAdapter {
    private static final String GET_POKEMON = "/{name}";
    private static final String GET_INTERNAL_POKEMON = "/internal/{name}";
    private static final String CREATE_POKEMON = "/internal/create";
    private final GetPokemon getPokemon;
    private final CreatePokemon createPokemon;
    private final Processor processor;

    public PokemoControllerAdapter(GetPokemon getPokemon, CreatePokemon createPokemon) {
        this.getPokemon = getPokemon;
        this.createPokemon = createPokemon;
        this.processor = new RequestProcessor();
    }

    @GetMapping(GET_POKEMON)
    public RestResponse<PokemonRest> getPokemon(final HttpServletRequest httpServletRequest, @PathVariable("name") String name) throws ExecutionException, InterruptedException {
        log.info("Llamada al servicio pokemon/{}", name);
        Pokemon pokemon = this.getPokemon.getPokemon(name);
        PokemonRest response = PokemonRest.toPokemonRest(pokemon);
        log.info("Respuesta del servicio pokemon/{}: [{}]", name, response);
        return processor.processRequest(Processor.Enriched.of(httpServletRequest),
                resp -> RestResponse.<PokemonRest>builder()
                        .data(response)
                        .id(resp.getId())
                        .status(HttpStatus.OK.value())
                        .resource(httpServletRequest.getRequestURI())
                        .metadata(processor.buildMetadata(resp.getReq()))
                        .build()
        );
    }

    @GetMapping(GET_INTERNAL_POKEMON)
    public RestResponse<PokemonRest> getInternalPokemon(final HttpServletRequest httpServletRequest,
                                                        @PathVariable("name") String name) {
        log.info("Llamada al servicio pokemon/internal/{}", name);
        Pokemon pokemon = this.getPokemon.getInternal(name);
        PokemonRest response = PokemonRest.toPokemonRest(pokemon);
        log.info("Respuesta del servicio pokemon/internal/{} : [{}]", name, response);
        return processor.processRequest(Processor.Enriched.of(httpServletRequest),
                resp -> RestResponse.<PokemonRest>builder()
                        .data(response)
                        .id(resp.getId())
                        .status(HttpStatus.OK.value())
                        .resource(httpServletRequest.getRequestURI())
                        .metadata(processor.buildMetadata(resp.getReq()))
                        .build()
        );
    }

    @PostMapping(CREATE_POKEMON)
    public RestResponse<Integer> createInternalPokemon(
            final HttpServletRequest httpServletRequest,
            @Valid @RequestBody PokemonRest request
    ){
        log.info("Llamada al servicio pokemon/internal");
        var response = createPokemon.createPokemon(request.toDomain());
        log.info("Respuesta del servicio pokemon/internal : [{}]", response);
        return processor.processRequest(Processor.Enriched.of(httpServletRequest),
                resp -> RestResponse.<Integer>builder()
                        .data(response)
                        .id(resp.getId())
                        .status(HttpStatus.CREATED.value())
                        .resource(httpServletRequest.getRequestURI())
                        .metadata(processor.buildMetadata(resp.getReq()))
                        .build()
        );

    }

}
