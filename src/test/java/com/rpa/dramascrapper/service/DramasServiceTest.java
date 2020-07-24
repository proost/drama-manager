package com.rpa.dramascrapper.service;

import com.rpa.dramascrapper.dto.Drama;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DramasServiceTest {

    @Autowired
    DramasService dramasService;
    Drama drama;

    @Before
    public void setUp() {
        this.drama = new Drama("DramasServiceTest", new Date());
    }

    @Test
    public void testAddNewDramaToDramas() throws Exception {
        Drama result = this.dramasService.addNewDramaToDramas(this.drama);

        assertNotEquals(0, result.getId());
        assertEquals(this.drama.getName(), result.getName());
        assertEquals(this.drama.getFirstAiringDate(), result.getFirstAiringDate());
    }

}