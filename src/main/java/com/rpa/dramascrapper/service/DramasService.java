package com.rpa.dramascrapper.service;

import com.rpa.dramascrapper.dao.DramaDao;
import com.rpa.dramascrapper.dto.Drama;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DramasService {

    private final DramaDao dramaDao;

    public DramasService(DramaDao dramaDao) {
        this.dramaDao = dramaDao;
    }

    @Transactional
    public Drama addNewDramaToDramas(Drama drama) throws NullPointerException{
        return this.dramaDao.insertNewDrama(drama);
    }

}
