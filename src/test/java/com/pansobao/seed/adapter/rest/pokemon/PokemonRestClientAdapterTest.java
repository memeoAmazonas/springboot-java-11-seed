package com.pansobao.seed.adapter.rest.pokemon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pansobao.seed.config.property.PokemonProperty;
import jdk.jfr.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.client.MockRestServiceServer;

@DisplayName("PokemonRestClientAdapterTest")
@Import(value = {PokemonProperty.class})
@AutoConfigureWebClient(registerRestTemplate = true)
@RestClientTest(value={PokemonRestClientAdapter.class})
class PokemonRestClientAdapterTest {

    @Autowired
    private PokemonRestClientAdapter adapter;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    MockRestServiceServer server;

    @Test
    @Name("When pokemon rest is success call")
    void getExternalRestPokemonOk(){
        // this.server.expect()
    }

}