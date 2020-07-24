package com.rpa.dramascrapper.dao;


import com.rpa.dramascrapper.dto.Drama;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DramaDaoTest {

    @Autowired
    DramaDao dramaDao;

    List<Drama> dramas;
    Drama dto;

    @Before
    public void setUp() {
        this.dramas = new ArrayList<>();

        Drama drama1 = new Drama(-1,"DaoTest1", new Date());
        Drama drama2 = new Drama(-2, "DaoTest2", new Date());
        this.dramas.add(drama1);
        this.dramas.add(drama2);

        this.dto = new Drama("DaoTest", new Date());
    }

    @Test
    public void testInsertNewDrama() throws Exception {
        Drama result = this.dramaDao.insertNewDrama(this.dto);

        assertNotEquals(0, result.getId());
        assertEquals(this.dto.getName(), result.getName());
        assertEquals(this.dto.getFirstAiringDate(), result.getFirstAiringDate());
    }

    @Test
    public void testSelectWatchedDramaById() throws Exception {
        Drama drama = this.dramaDao.insertNewDrama(this.dto);
        List<Drama> dramas = new ArrayList<>();
        dramas.add(drama);
        this.dramaDao.enrollDramasInWatched(dramas);

        Map<String, Object> expected = new HashMap<>();
        expected.put("dramaId", Integer.valueOf(drama.getId()));

        Map<String, Object> result = this.dramaDao.selectWatchedDramaById(expected);

        assertEquals(drama.getId(), result.get("id"));
        assertEquals(drama.getName(), result.get("name"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals(format.format(drama.getFirstAiringDate()), format.format(result.get("first_airing_date")));

    }

    @Test
    public void testEnrollDramaInReserved() throws Exception {
        this.dto.setId(-1);

        assertEquals(1, this.dramaDao.enrollDramaInReserved(this.dto));
    }

    @Test
    public void testEnrollDramaInWatched() throws Exception {
        int[] expected = {1, 1};
        assertArrayEquals(expected, this.dramaDao.enrollDramasInWatched(this.dramas));
    }

    @Test
    public void testSelectReservedDramas() throws Exception {
        for(Drama drama: this.dramas) {
            Drama insertedDrama = this.dramaDao.insertNewDrama(drama);
            this.dramaDao.enrollDramaInReserved(insertedDrama);
        }

        List<Drama> result = this.dramaDao.selectReservedDramas();

        assertNotEquals(0, result.size());
    }

    @Test
    public void testSelectWatchedDramas() throws Exception {
        List<Drama> insertedDramas = new ArrayList<>();
        for(Drama drama: this.dramas) {
            Drama insertedDrama = this.dramaDao.insertNewDrama(drama);
            insertedDramas.add(insertedDrama);
        }

        this.dramaDao.enrollDramasInWatched(insertedDramas);
        List<Map<String, Object>> result = this.dramaDao.selectWatchedDramas();

        assertNotEquals(0, result.size());
    }

    @Test
    public void testDeleteDramaFromReserved() throws Exception {
        List<Drama> dramas = new ArrayList<>();
        for(Drama drama: this.dramas) {
            Drama insertedDrama = this.dramaDao.insertNewDrama(drama);
            this.dramaDao.enrollDramaInReserved(insertedDrama);
            dramas.add(insertedDrama);
        }

        int[] expected = {1, 1};
        assertArrayEquals(expected, this.dramaDao.deleteDramasFromReserved(dramas));
    }

    @Test
    public void testDeleteDramaFromWatched() throws Exception {
        List<Drama> dramas = new ArrayList<>();
        List<Map<String, Object>> watchedDramas = new ArrayList<>();

        for(Drama drama: this.dramas) {
            Drama insertedDrama = this.dramaDao.insertNewDrama(drama);
            dramas.add(insertedDrama);

            Map<String, Object> temp = new HashMap<>();
            temp.put("dramaId", drama.getId());

            watchedDramas.add(temp);
        }

        System.out.println(dramas);
        this.dramaDao.enrollDramasInWatched(dramas);

        int[] expected = {1, 1};
        assertArrayEquals(expected, this.dramaDao.deleteWatchedDramas(watchedDramas));
    }

    @Test
    public void testUpdateDramaLastWatchedSeason() throws Exception {
        List<Drama> dramas = new ArrayList<>();
        for(Drama drama: this.dramas) {
            Drama insertedDrama = this.dramaDao.insertNewDrama(drama);
            dramas.add(insertedDrama);
        }
        this.dramaDao.enrollDramasInWatched(dramas);

        List<Map<String, Object>> watchedDramas = this.dramaDao.selectWatchedDramas();
        Map<String, Object> watchedDrama = watchedDramas.get(0);
        watchedDrama.put("dramaId", watchedDrama.get("id"));

        Integer lastWatchedSeason = (Integer) watchedDrama.get("last_watched_season");
        watchedDrama.put("lastWatchedSeason", lastWatchedSeason - 100);

        assertEquals(1, this.dramaDao.updateDramaLastWatchedSeasonById(watchedDrama));
    }


}
