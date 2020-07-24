package com.rpa.dramascrapper.controller;

import com.rpa.dramascrapper.service.WatchedDramasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/watched-drama/{dramaId}")
public class WatchedDramaController {

    private WatchedDramasService watchedDramasService;

    @Autowired
    public WatchedDramaController(WatchedDramasService watchedDramasService) {
        this.watchedDramasService = watchedDramasService;
    }

    @GetMapping
    public ModelAndView getWatchedDrama(@PathVariable Integer dramaId) {

        ModelAndView mv = new ModelAndView("watched-drama");
        Map<String, Object> watchedDrama = this.watchedDramasService.getWatchedDramaById(dramaId);
        mv.addObject("watchedDrama", watchedDrama);
        return mv;
    }

    @PostMapping
    public ResponseEntity updateWatchedDrama(
            @PathVariable Integer dramaId,
            @RequestBody Map<String, String> json
    ) {
        Boolean result = this.watchedDramasService.updateDramaLastWatchedSeason(dramaId, Integer.valueOf(json.get("lastWatchedSeason")));

        if (result) {
            return new ResponseEntity<>("성공했습니다.", HttpStatus.OK);
        } else if (result.equals(false)) {
            log.warn("마지막으로 본 시즌을 변경하지 않고, 변경을 시도함");
            return new ResponseEntity<>("바뀐 사항이 없습니다.", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("서버에 이상이 있습니다.", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
