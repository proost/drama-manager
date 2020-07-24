package com.rpa.dramascrapper.service;


import com.rpa.dramascrapper.dto.Drama;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReservedDramasServiceTest {

    @Autowired ReservedDramasService reservedDramasService;
    @Autowired DramasService dramasService;

    Drama drama;

    @Before
    public void setUp() {
        this.drama = this.dramasService.addNewDramaToDramas(
                new Drama("ReservedDramasTest", new Date())
        );
    }

    @Test
    public void testAddDramaToReserved() throws Exception {
        assertEquals(true, this.reservedDramasService.addDramaToReserved(this.drama));
    }

    @Test
    public void testGetAllReservedDramas() throws Exception {
        this.reservedDramasService.addDramaToReserved(this.drama);

        assertEquals(false, this.reservedDramasService.getAllReservedDramas().isEmpty());
    }

    @Test
    public void testRemoveDramasFromReservedDramas() throws Exception {
        this.reservedDramasService.addDramaToReserved(this.drama);

        List<Drama> dramaToBeRemoved = new ArrayList<>();
        dramaToBeRemoved.add(this.drama);

        assertEquals(true, this.reservedDramasService.removeDramasFromReservedDramas(dramaToBeRemoved));
    }
}
