package com.cinema.demo.booking_apis.apis;


import com.cinema.demo.booking_apis.dtos.SeatDTO;
import com.cinema.demo.booking_apis.services.ISeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/seats", produces = "application/json")
public class SeatApi {
    @Autowired
    private ISeatService seatService;

    @GetMapping
    public List<SeatDTO> getSeatsByShowtimeId(@RequestParam Integer showtimeId){
        return seatService.getSeatsByShowTimeId(showtimeId);
    }

    @PostMapping("/update-status")
    public ResponseEntity<Map<String, Object>> updateSeatStatus(@RequestBody SeatDTO request) {
        try {
            SeatDTO seatDTO = seatService.getSeatById(request.getSeatId());
            seatDTO.setIsOccupied(request.getIsOccupied());
            seatService.updateSeatStatus(seatDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

