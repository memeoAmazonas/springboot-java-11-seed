package com.pansobao.seed.adapter.rest.pokemon;

import com.pansobao.seed.adapter.rest.exception.BadRequestRestClientException;
import com.pansobao.seed.adapter.rest.exception.EmptyOrNullBodyRestClientException;
import com.pansobao.seed.adapter.rest.exception.NotFoundRestClientException;
import com.pansobao.seed.adapter.rest.exception.TimeoutRestClientException;
import com.pansobao.seed.adapter.rest.handler.RestTemplateErrorHandler;
import com.pansobao.seed.adapter.rest.pokemon.model.type.TypeModel;
import com.pansobao.seed.application.port.out.TypeRepository;
import com.pansobao.seed.config.ErrorCode;
import com.pansobao.seed.config.property.PokemonProperty;
import com.pansobao.seed.domain.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class TypeRestClientAdapter implements TypeRepository {
    private final PokemonProperty property;
    private final RestTemplate restTemplate;

    public TypeRestClientAdapter(PokemonProperty property, RestTemplate restTemplate) {
        this.property = property;
        this.restTemplate = restTemplate;
        var errorHandler = new RestTemplateErrorHandler(
                Map.of(
                        HttpStatus.NOT_FOUND, new NotFoundRestClientException(ErrorCode.TYPE_NOT_FOUND),
                        HttpStatus.REQUEST_TIMEOUT, new TimeoutRestClientException(ErrorCode.TYPE_TIMEOUT),
                        HttpStatus.BAD_REQUEST, new BadRequestRestClientException(ErrorCode.TYPE_BAD_REQUEST)
                )
        );
        this.restTemplate.setErrorHandler(errorHandler);
    }

    @Override
    public Type getType(String name) {
        log.info("Servicio obtener el type del pokemon, buscar: [{}]" ,name);
        TypeModel response = Optional.ofNullable(restTemplate.getForObject(property.getUrl(property.getUrlType(), name),TypeModel.class,name))
                .orElseThrow(()-> new EmptyOrNullBodyRestClientException(ErrorCode.TYPE_NOT_FOUND));
        log.info("Respuesta obtenida desde el servicio obtener el type del pokemon data: [{}]", response);
        return response.toTypeDomain();
    }
}
