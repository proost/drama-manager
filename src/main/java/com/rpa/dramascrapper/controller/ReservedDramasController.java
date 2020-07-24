package com.rpa.dramascrapper.controller;


import com.rpa.dramascrapper.dto.Drama;
import com.rpa.dramascrapper.service.ReservedDramasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reserved-dramas")
public class ReservedDramasController {

    private ReservedDramasService reservedDramasService;

    @Autowired
    public ReservedDramasController( ReservedDramasService reservedDramasService) {
        this.reservedDramasService = reservedDramasService;
    }

    @GetMapping
    public ModelAndView getReservedDramas() {
        ModelAndView mv = new ModelAndView("reserved-dramas");
        List<Drama> reservedDramas = this.reservedDramasService.getAllReservedDramas();
        mv.addObject("reservedDramas", reservedDramas);
        return mv;
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity removeReservedDramas(@RequestBody List<Drama> reservedDramasToBeRemoved) {
        if (reservedDramasToBeRemoved.isEmpty()) {
            log.error("Message: 0건의 예약된 드라마를 삭제하려고 시도함");
           return new ResponseEntity<>("최소 한 건 이상 삭제해주세요", HttpStatus.BAD_REQUEST);
        }

        Boolean result = this.reservedDramasService.removeDramasFromReservedDramas(reservedDramasToBeRemoved);

        if (result) {
            return new ResponseEntity("성공했습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("서버에 이상이 있습니다.", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
