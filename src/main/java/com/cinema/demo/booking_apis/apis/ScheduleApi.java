package com.cinema.demo.booking_apis.apis;

import com.cinema.demo.booking_apis.dtos.ShowtimeDTO;
import com.cinema.demo.booking_apis.services.IShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/schedule", produces = "application/json")
public class ScheduleApi {
    @Autowired
    private IShowTimeService scheduleService;

    @GetMapping("/start-times")
    public List<String> getStartTimes(@RequestParam Integer movieId,
                                         @RequestParam String startDate) {
        return scheduleService.getStartTimes(movieId,LocalDate.parse(startDate));
    }

    @GetMapping("/schedule-times")
    public List<ShowtimeDTO> getSchedules(@RequestParam Integer movieId,
                                          @RequestParam String startDate,
                                          @RequestParam String startTime,
                                          @RequestParam Integer roomId){
        return scheduleService.getSchedules(movieId,startDate,startTime,roomId);
    }
}
