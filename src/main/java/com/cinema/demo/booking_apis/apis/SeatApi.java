package com.cinema.demo.booking_apis.apis;


import com.cinema.demo.booking_apis.dtos.SeatDTO;
import com.cinema.demo.booking_apis.services.ISeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/seats")
public class SeatApi {
    @Autowired
    private ISeatService seatService;

    @GetMapping
    public List<SeatDTO> getSeatsByScheduleId(@RequestParam Integer scheduleId){
        return seatService.getSeatsByShowTimeId(scheduleId);
    }
}
