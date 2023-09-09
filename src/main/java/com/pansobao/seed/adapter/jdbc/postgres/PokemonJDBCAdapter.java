package com.pansobao.seed.adapter.jdbc.postgres;

import com.pansobao.seed.adapter.jdbc.dao.sql.GenericDAO;
import com.pansobao.seed.adapter.jdbc.dao.sql.SqlReader;
import com.pansobao.seed.adapter.jdbc.postgres.model.PokemonJDBCModel;
import com.pansobao.seed.adapter.rest.exception.BadRequestRestClientException;
import com.pansobao.seed.application.port.out.PokemonJDBCRepository;
import com.pansobao.seed.config.ErrorCode;
import com.pansobao.seed.domain.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PokemonJDBCAdapter implements PokemonJDBCRepository {

    private static final String SQL_GET_POKEMON = "sql/get-pokemon.sql";
    private static final String SQL_INSERT_POKEMON = "sql/insert-pokemon.sql";
    private static final String KEY_POKEMON_NAME = "name";
    private static final String KEY_POKEMON_ABILITY = "ability";
    private final GenericDAO dao;

    private final String getPokemon;
    private final String insertPokemon;


    public PokemonJDBCAdapter(final GenericDAO dao) {
        this.dao = dao;
        this.getPokemon = SqlReader.read(SQL_GET_POKEMON);
        this.insertPokemon = SqlReader.read(SQL_INSERT_POKEMON);
    }

    @Override
    public Integer createPokemon(Pokemon pokemon) {
        log.info("Insertando un nuevo pokemon en la BD [{}]", pokemon);
        var params = new MapSqlParameterSource()
                .addValue(KEY_POKEMON_NAME, pokemon.getName())
                        .addValue(KEY_POKEMON_ABILITY, pokemon.getAbility().getName());
        return dao.insert(insertPokemon,params,null).intValue();
    }

    @Override
    public Pokemon getPokemonByName(String name) {
        var parameter = new MapSqlParameterSource().addValue(KEY_POKEMON_NAME, name);
            log.info("Se va a realizar la busqueda del pokemon cuyo nombre es: [{}]", name);
            var response = dao.findOne(getPokemon, parameter, PokemonJDBCModel.class)
                    .orElseThrow(()-> new BadRequestRestClientException(ErrorCode.POKEMON_NOT_FOUND));
            return response.toDomain();
    }
}
