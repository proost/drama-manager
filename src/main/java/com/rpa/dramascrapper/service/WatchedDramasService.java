package com.rpa.dramascrapper.service;

import com.rpa.dramascrapper.dao.DramaDao;
import com.rpa.dramascrapper.dto.Drama;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class WatchedDramasService {

    private final DramaDao dramaDao;

    @Autowired
    public WatchedDramasService(DramaDao dramaDao) {
        this.dramaDao = dramaDao;
    }

    private Boolean addDramasToWatchedWrapped(List<Drama> Dramas) throws DataAccessException, NullPointerException{
        int[] results = this.dramaDao.enrollDramasInWatched(Dramas);

        return Arrays.stream(results).allMatch(result -> result == 1);
    }

    @Transactional
    public Boolean addDramasToWatched(Drama drama) {
        List<Drama> dramas = new ArrayList<>();
        dramas.add(drama);

        return this.addDramasToWatchedWrapped(dramas);
    }

    @Transactional
    public Boolean addDramasToWatched(List<Drama> dramas) {
        return this.addDramasToWatchedWrapped(dramas);
    }

    @Transactional
    public List<Map<String, Object>> getAllWatchedDramas() throws DataAccessException, NullPointerException{
        return this.dramaDao.selectWatchedDramas();
    }

    @Transactional
    public Map<String, Object> getWatchedDramaById(Integer dramaId) throws DataAccessException, NullPointerException{
        Map<String, Object> params = new HashMap<>();
        params.put("dramaId", dramaId);

        return this.dramaDao.selectWatchedDramaById(params);
    }

    @Transactional
    public Boolean removeWatchedDramas(List<Map<String, Object>> watchedDramas) throws DataAccessException, NullPointerException{
        int[] results = this.dramaDao.deleteWatchedDramas(watchedDramas);

        return Arrays.stream(results).allMatch(Result -> Result == 1);
    }

    @Transactional
    public Boolean updateDramaLastWatchedSeason(Integer dramaId, Integer lastWatchedSeason) throws DataAccessException, NullPointerException {
        Map<String, Object> watchedDrama = new HashMap<>();
        watchedDrama.put("dramaId", dramaId);
        watchedDrama.put("lastWatchedSeason", lastWatchedSeason);

        return this.dramaDao.updateDramaLastWatchedSeasonById(watchedDrama) == 1;
    }

}
