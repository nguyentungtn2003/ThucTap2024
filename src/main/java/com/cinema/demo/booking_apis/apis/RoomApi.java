package com.cinema.demo.booking_apis.apis;

import com.cinema.demo.booking_apis.dtos.CinemaRoomDTO;
import com.cinema.demo.booking_apis.services.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/rooms")
public class RoomApi {
    @Autowired
    private IRoomService roomService;

    @GetMapping
    public List<CinemaRoomDTO> getRooms(@RequestParam Integer movieId,
                                        @RequestParam String startDate,
                                        @RequestParam String startTime){
        return roomService.getRooms(movieId, startDate, startTime);
    }
}
