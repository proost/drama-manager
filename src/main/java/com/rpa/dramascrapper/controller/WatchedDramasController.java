package com.rpa.dramascrapper.controller;


import com.rpa.dramascrapper.dto.Drama;
import com.rpa.dramascrapper.service.WatchedDramasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/watched-dramas")
public class WatchedDramasController {

    private WatchedDramasService watchedDramasService;

    @Autowired
    public WatchedDramasController(WatchedDramasService watchedDramasService) {
        this.watchedDramasService = watchedDramasService;
    }

    @GetMapping
    public ModelAndView getWatchedDramas() {
        ModelAndView mv = new ModelAndView("watched-dramas");
        List<Map<String, Object>> watchedDramas = this.watchedDramasService.getAllWatchedDramas();
        mv.addObject("watchedDramas", watchedDramas);
        return mv;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity addWatchedDramas(@RequestBody List<Drama> reservedDramasToBeWatched) {
        if (reservedDramasToBeWatched.isEmpty()) {
            log.error("Message: 0건의 예약된 드라마를 본 드라마로 등록하려고 시도함");
            return new ResponseEntity<>("최소 한 건 이상 등록해주세요", HttpStatus.BAD_REQUEST);
        }

        Boolean result = this.watchedDramasService.addDramasToWatched(reservedDramasToBeWatched);

        if (result) {
            return new ResponseEntity<>("성공했습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("서버에 이상이 있습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity removeWatchedDramas(@RequestBody List<Map<String, Object>> watchedDramas) {
        if (watchedDramas.isEmpty()) {
            log.error("Message: 0건의 예약된 드라마를 삭제하려고 시도함");
            return new ResponseEntity<>("최소 한 건 이상 삭제해주세요", HttpStatus.BAD_REQUEST);
        }

        Boolean result = this.watchedDramasService.removeWatchedDramas(watchedDramas);

        if (result) {
            return new ResponseEntity<>("성공했습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("서버에 이상이 있습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
