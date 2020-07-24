package com.rpa.dramascrapper.controller;

import com.rpa.dramascrapper.dto.Drama;
import com.rpa.dramascrapper.service.DramasService;
import com.rpa.dramascrapper.service.ReservedDramasService;
import com.rpa.dramascrapper.service.WatchedDramasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

    DramasService dramasService;
    ReservedDramasService reservedDramasService;
    WatchedDramasService watchedDramasService;

    @Autowired
    public EnrollmentController(
            DramasService dramasService,
            ReservedDramasService reservedDramasService,
            WatchedDramasService watchedDramasService
    ) {
        this.dramasService = dramasService;
        this.reservedDramasService = reservedDramasService;
        this.watchedDramasService = watchedDramasService;
    }

    @GetMapping
    public ModelAndView getEnrollment() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("enrollment");
        return mv;
    }

    @PostMapping
    public ResponseEntity postEnrollment(
            @RequestParam String name,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam Date firstAiringDate,
            @RequestParam String dramaType
            ) {

        Drama drama = this.dramasService.addNewDramaToDramas(
                new Drama(name, firstAiringDate)
        );

        boolean isOk;
        if (dramaType.equals("reserved")) {
            isOk = this.reservedDramasService.addDramaToReserved(drama);
        } else {
            isOk = this.watchedDramasService.addDramasToWatched(drama);
        }

        if (isOk) {
            return new ResponseEntity<>("성공했습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("서버에 이상이 있습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
