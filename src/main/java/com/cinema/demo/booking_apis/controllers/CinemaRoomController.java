package com.cinema.demo.booking_apis.controllers;

import com.cinema.demo.booking_apis.dtos.CinemaRoomDTO;
import com.cinema.demo.booking_apis.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/cinema-rooms")
public class CinemaRoomController {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${server.baseUrl}")
    private String baseUrl;

    private final RoomService roomService;
    public CinemaRoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    @GetMapping
    public String displayCinemaRoomsPage(@RequestParam int movieId, @RequestParam String startDate, Model model) {
        String apiUrl = baseUrl + "/api/available-rooms?movieId=" + movieId + "&startDate=" + startDate;

        ResponseEntity<CinemaRoomDTO[]> response = restTemplate.getForEntity(apiUrl, CinemaRoomDTO[].class);
        List<CinemaRoomDTO> rooms = List.of(response.getBody());

        model.addAttribute("movieId", movieId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("rooms", rooms);

        return "boleto/demo/movie-ticket-plan";
    }

    // API trả danh sách phòng chiếu dưới dạng JSON
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<CinemaRoomDTO>> getCinemaRooms(@RequestParam int movieId,
                                                              @RequestParam String startDate,
                                                              @RequestParam String startTime) {
        List<CinemaRoomDTO> rooms = roomService.getRooms(movieId, startDate, startTime);
        return ResponseEntity.ok(rooms);
    }
}