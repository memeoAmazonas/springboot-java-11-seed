package com.pansobao.seed.adapter.controller.pokemon;

import com.pansobao.seed.adapter.rest.exception.BadRequestRestClientException;
import com.pansobao.seed.adapter.rest.exception.EmptyOrNullBodyRestClientException;
import com.pansobao.seed.application.port.in.CreatePokemon;
import com.pansobao.seed.application.port.in.GetPokemon;
import com.pansobao.seed.config.ErrorCode;
import com.pansobao.seed.mocks.PokemonMock;
import com.pansobao.seed.utils.UtilsByTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@DisplayName("PokemoControllerAdapterTest")
@WebMvcTest(PokemoControllerAdapter.class)
@ExtendWith(MockitoExtension.class)
class PokemoControllerAdapterTest {
    private static final String GET_POKEMON = "/api/v1/pokemon/%s";
    private static final String GET_INTERNAL_POKEMON = "/api/v1/pokemon/internal/%s";

    @Autowired
    private MockMvc restRequest;

    @MockBean
    private GetPokemon getPokemon;

    @MockBean
    private CreatePokemon createPokemon;

    @Test
    @DisplayName("When Get pokemon is success")
    void getPokemonSuccessExternal() throws Exception {
        when(getPokemon.getPokemon(PokemonMock.STRING_VALUE)).thenReturn(PokemonMock.getPokemonDomain());
        restRequest.perform(MockMvcRequestBuilders.get(GET_POKEMON))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("When Get pokemon is fail bad request")
    void getPokemonBadRequestExternal() throws Exception {
        when(getPokemon.getPokemon(anyString())).thenThrow(new BadRequestRestClientException(ErrorCode.POKEMON_NOT_FOUND));
        restRequest.perform(MockMvcRequestBuilders.get(GET_POKEMON))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("When Get pokemon is fail EmptyOrNullBodyRestClientException")
    void getPokemonEmptyOrNullBodyExternal() throws Exception {
        when(getPokemon.getPokemon(anyString())).thenThrow(new EmptyOrNullBodyRestClientException(ErrorCode.POKEMON_NOT_FOUND));
        restRequest.perform(MockMvcRequestBuilders.get(GET_POKEMON))
                .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("When Get pokemon internal is success")
    void getPokemonSuccessInternal() throws Exception {
        when(getPokemon.getInternal(PokemonMock.STRING_VALUE)).thenReturn(PokemonMock.getPokemonDomain());
        restRequest.perform(MockMvcRequestBuilders.get(GET_INTERNAL_POKEMON))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("When pokemon is create success")
    void createPokemonSuccessInternal() throws Exception {
        when(createPokemon.createPokemon(PokemonMock.getPokemonDomain())).thenReturn(0);
        restRequest.perform(MockMvcRequestBuilders.post(String.format(PokemonMock.CREATE_POKEMON))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UtilsByTest.JsonToString(PokemonMock.getPokemonRest())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(UtilsByTest.JsonToString(PokemonMock.getRestResponseCreatePokemon())));
/*        restRequest.perform(MockMvcRequestBuilders.post(CREATE_POKEMON))
                .andExpect(status().isOk());*/
    }


}