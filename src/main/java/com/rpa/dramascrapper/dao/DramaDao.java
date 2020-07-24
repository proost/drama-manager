package com.rpa.dramascrapper.dao;

import com.rpa.dramascrapper.dto.Drama;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class DramaDao {

    private static final String DRAMAS_TABLE = "dramas";

    private final NamedParameterJdbcTemplate jdbc;
    private final SimpleJdbcInsert dramaInsertAction;
    private final RowMapper<Drama> dramaRowMapper = BeanPropertyRowMapper.newInstance(Drama.class);

    @Autowired
    public DramaDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.dramaInsertAction = new SimpleJdbcInsert(dataSource).withTableName(DramaDao.DRAMAS_TABLE).usingGeneratedKeyColumns("id");
    }

    private SqlParameterSource[] castDramasToSqlParams(List<Drama> dramas) {
        return  dramas.stream()
                    .map(BeanPropertySqlParameterSource::new)
                    .toArray(SqlParameterSource[]::new);
    }

    public Drama insertNewDrama(Drama drama) throws NullPointerException, DataAccessException {
        SqlParameterSource params = new BeanPropertySqlParameterSource(drama);

        Number dramaId = this.dramaInsertAction.executeAndReturnKey(params);
        drama.setId(dramaId.intValue());
        return drama;
    }

    public int enrollDramaInReserved(Drama drama) throws DataAccessException, NullPointerException {
        SqlParameterSource params = new BeanPropertySqlParameterSource(drama);

        return this.jdbc.update(DaoSqls.INSERT_DRAMA_INTO_RESERVED, params);
    }

    public List<Drama> selectReservedDramas() throws DataAccessException, NullPointerException {
        return this.jdbc.query(DaoSqls.SELECT_RESERVED_DRAMAS, Collections.emptyMap(), this.dramaRowMapper);
    }

    public int[] deleteDramasFromReserved(List<Drama> dramas) throws DataAccessException, NullPointerException {
        SqlParameterSource[] params = this.castDramasToSqlParams(dramas);

        return this.jdbc.batchUpdate(DaoSqls.DELETE_DRAMAS_FROM_RESERVED, params);
    }

    public List<Map<String, Object>> selectWatchedDramas() throws DataAccessException, NullPointerException {
        return this.jdbc.queryForList(DaoSqls.SELECT_WATCHED_DRAMAS, Collections.emptyMap());
    }

    public Map<String, Object> selectWatchedDramaById(Map<String, Object> params) throws DataAccessException, NullPointerException {
        return this.jdbc.queryForMap(DaoSqls.SELECT_WATCHED_DRAMA_BY_ID, params);
    }

    public int[] enrollDramasInWatched(List<Drama> dramas) throws DataAccessException, NullPointerException {
        SqlParameterSource[] params = this.castDramasToSqlParams(dramas);

        return this.jdbc.batchUpdate(DaoSqls.INSERT_DRAMAS_INTO_WATCHED, params);
    }

    public int[] deleteWatchedDramas(List<Map<String, Object>> dramas) throws DataAccessException, NullPointerException {
        Map<String, Object>[] params = dramas.toArray(new Map[dramas.size()]);

        return this.jdbc.batchUpdate(DaoSqls.DELETE_DRAMAS_FROM_WATCHED, params);
    }

    public int updateDramaLastWatchedSeasonById(Map<String, Object> params) throws DataAccessException, NullPointerException {
        return this.jdbc.update(DaoSqls.UPDATE_DRAMA_LAST_WATCHED_SEASON, params);
    }

}

