package com.pansobao.seed.adapter.jdbc.postgres;

import com.pansobao.seed.adapter.jdbc.dao.GenericDAO;
import com.pansobao.seed.adapter.jdbc.dao.SqlReader;
import com.pansobao.seed.adapter.jdbc.postgres.model.PokemonJDBCModel;
import com.pansobao.seed.application.port.out.PokemonJDBCRepository;
import com.pansobao.seed.domain.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class PokemonJDBCAdapter implements PokemonJDBCRepository {

    private static final String SQL_GET_POKEMON = "sql/get-pokemon.sql";
    private static final String KEY_POKEMON_NAME = "name";
    private final GenericDAO dao;

    private final String getPokemon;


    public PokemonJDBCAdapter(final GenericDAO dao) {
        this.dao = dao;
        this.getPokemon = SqlReader.read(SQL_GET_POKEMON);
    }

    @Override
    public Integer createPokemon(Pokemon pokemon) {
        return null;
    }

    @Override
    public Optional<Pokemon> getPokemonByName(String name) {
        var parameter = new MapSqlParameterSource().addValue(KEY_POKEMON_NAME, name);
        try {
            log.info("Se va a realizar la busqueda del pokemon cuyo nombre es: [{}]", name);
            return dao.findOne(getPokemon, parameter, PokemonJDBCModel.class).map(PokemonJDBCModel::toDomain);
        } catch (DataAccessException e) {
            log.error("Ocurrio un error buscando el pokemon de nombre {}", name, e);
            throw e;
        }
    }
}
