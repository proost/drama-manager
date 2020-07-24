package com.rpa.dramascrapper.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity handleNullPointerExceptionGlobal(NullPointerException e) {
        log.error("Message: ", e);
        return new ResponseEntity<>("DB를 다시 한 번 확인해주세요", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("Message: ", e);
        return new ResponseEntity<>("이미 드라마가 존재합니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataRetrievalFailureException.class)
    public ResponseEntity handleDataRetrievalFailureException(DataRetrievalFailureException e) {
        log.error("Message: ", e);
        return new ResponseEntity<>("다시 시도해주세요", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(QueryTimeoutException.class)
    public ResponseEntity handleQueryTimeoutException(QueryTimeoutException e) {
        log.error("Message: ", e);
        return new ResponseEntity<>("다시 시도해주세요", HttpStatus.SERVICE_UNAVAILABLE);
    }

}
