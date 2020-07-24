package com.rpa.dramascrapper.service;

import com.rpa.dramascrapper.dao.DramaDao;
import com.rpa.dramascrapper.dto.Drama;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ReservedDramasService {

    private final DramaDao dramaDao;

    @Autowired
    public ReservedDramasService(DramaDao dramaDao) {
        this.dramaDao = dramaDao;
    }

    @Transactional
    public Boolean addDramaToReserved(Drama drama) throws DataAccessException, NullPointerException {
        return this.dramaDao.enrollDramaInReserved(drama) == 1;
    }

    @Transactional
    public List<Drama> getAllReservedDramas() throws DataAccessException, NullPointerException {
        return this.dramaDao.selectReservedDramas();
    }

    @Transactional
    public Boolean removeDramasFromReservedDramas(List<Drama> dramas) throws DataAccessException, NullPointerException {
        int[] results = this.dramaDao.deleteDramasFromReserved(dramas);

        return Arrays.stream(results).allMatch(Result -> Result == 1);
    }

}
