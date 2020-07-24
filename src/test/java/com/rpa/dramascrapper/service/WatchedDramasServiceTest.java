package com.rpa.dramascrapper.service;

import com.rpa.dramascrapper.dto.Drama;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class WatchedDramasServiceTest {

    @Autowired
    WatchedDramasService watchedDramasService;

    @Autowired
    DramasService dramasService;

    List<Drama> dramas;
    Drama drama;

    @Before
    public void setUp() {
        this.dramas = new ArrayList<>();
        this.dramas.add(
                this.dramasService.addNewDramaToDramas(new Drama("WatchedDramasServiceTest1", new Date()))
        );
        this.dramas.add(
                this.dramasService.addNewDramaToDramas(new Drama("WatchedDramasServiceTest2", new Date()))
        );

        this.drama = this.dramasService.addNewDramaToDramas(
                new Drama("WatchedDramasServiceTest", new Date())
        );
    }

    @Test
    public void testAddDramasToWatched() throws Exception {
        assertEquals(true, this.watchedDramasService.addDramasToWatched(this.dramas));
        assertEquals(true, this.watchedDramasService.addDramasToWatched(this.drama));
    }

    @Test
    public void getAllWatchedDramas() throws Exception {
        this.watchedDramasService.addDramasToWatched(this.dramas);

        assertEquals(false, this.watchedDramasService.getAllWatchedDramas().isEmpty());
    }

    @Test
    public void getWatchedDramaById() {
        this.watchedDramasService.addDramasToWatched(this.drama);

        Map<String, Object> result = this.watchedDramasService.getWatchedDramaById(Integer.valueOf(this.drama.getId()));

        assertEquals(drama.getId(), result.get("id"));
        assertEquals(drama.getName(), result.get("name"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals(format.format(drama.getFirstAiringDate()), format.format(result.get("first_airing_date")));

    }

    @Test
    public void removeDramasFromWatchedDramas() throws Exception {
        this.watchedDramasService.addDramasToWatched(this.dramas);

        List<Map<String, Object>> watchedDramas = new ArrayList<>();
        for (Drama drama: this.dramas) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("dramaId", drama.getId());
            watchedDramas.add(temp);
        }

        assertEquals(true, this.watchedDramasService.removeWatchedDramas(watchedDramas));
    }

    @Test
    public void updateDramaLastWatchedSeason() throws Exception {
        this.watchedDramasService.addDramasToWatched(this.drama);

        List<Map<String, Object>> watchedDramas = this.watchedDramasService.getAllWatchedDramas();
        Map<String, Object> watchedDrama = watchedDramas.get(0);

        assertEquals(true, this.watchedDramasService.updateDramaLastWatchedSeason(
                (Integer) watchedDrama.get("id"), (Integer) watchedDrama.get("last_watched_season"))
        );
    }
}