package com.pansobao.seed.adapter.rest.pokemon;

import com.pansobao.seed.adapter.rest.exception.BadRequestRestClientException;
import com.pansobao.seed.adapter.rest.exception.EmptyOrNullBodyRestClientException;
import com.pansobao.seed.adapter.rest.exception.NotFoundRestClientException;
import com.pansobao.seed.adapter.rest.exception.TimeoutRestClientException;
import com.pansobao.seed.adapter.rest.handler.RestTemplateErrorHandler;
import com.pansobao.seed.adapter.rest.pokemon.model.ability.AbilityModel;
import com.pansobao.seed.application.port.out.AbilityRepository;
import com.pansobao.seed.config.ErrorCode;
import com.pansobao.seed.config.property.PokemonProperty;
import com.pansobao.seed.domain.Ability;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class AbilityRestClientAdapter implements AbilityRepository {
    private final PokemonProperty property;
    private final RestTemplate restTemplate;

    public AbilityRestClientAdapter(PokemonProperty property, RestTemplate restTemplate) {
        this.property = property;
        this.restTemplate = restTemplate;
        var errorHandler = new RestTemplateErrorHandler(
                Map.of(
                        HttpStatus.NOT_FOUND, new NotFoundRestClientException(ErrorCode.ABILITY_NOT_FOUND),
                        HttpStatus.REQUEST_TIMEOUT, new TimeoutRestClientException(ErrorCode.ABILITY_TIMEOUT),
                        HttpStatus.BAD_REQUEST, new BadRequestRestClientException(ErrorCode.ABILITY_BAD_REQUEST)
                )
        );
        this.restTemplate.setErrorHandler(errorHandler);
    }

    @Override
    public Ability getAbility(String name) {
        log.info("Servicio obtener las habilidades del pokemon, buscar: [{}]" ,name);
        AbilityModel response = Optional.ofNullable(restTemplate.getForObject(property.getUrl(property.getUrlAbility(), name),AbilityModel.class,name))
                .orElseThrow(()-> new EmptyOrNullBodyRestClientException(ErrorCode.ABILITY_NOT_FOUND));
        log.info("Respuesta obtenida desde el servicio habilidades del pokemon data: [{}]", response);
        return response.toAbilityDomain();
    }
}
