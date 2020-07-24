package com.rpa.dramascrapper.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LoggingTest {

    @Test
    public void logging() {
        log.trace("Trace Level 테스트");
        log.debug("DEBUG Level 테스트");
        log.info("INFO Level 테스트");
        log.warn("Warn Level 테스트");
        log.error("ERROR Level 테스트");
    }
}

