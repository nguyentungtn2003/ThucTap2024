package com.cinema.demo.booking_apis.controllers;

import com.cinema.demo.booking_apis.dtos.ShowtimeDTO;
import com.cinema.demo.booking_apis.services.ShowTimeService;
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
@RequestMapping("/show-times")
public class ShowtimeControllerUser {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${server.baseUrl}")
    private String baseUrl;

    private final ShowTimeService showtimeService;

    public ShowtimeControllerUser(ShowTimeService showtimeService) {
        this.showtimeService = showtimeService;
    }
    @GetMapping
    public String displayShowtimePage(@RequestParam int movieId, @RequestParam String startDate,
                                      @RequestParam int roomId, Model model) {
        String apiUrl = baseUrl + "/api/schedule/showtimes?movieId=" + movieId
                + "&startDate=" + startDate + "&roomId=" + roomId;

        ResponseEntity<ShowtimeDTO[]> response = restTemplate.getForEntity(apiUrl, ShowtimeDTO[].class);
        List<ShowtimeDTO> showtimes = List.of(response.getBody());

        model.addAttribute("movieId", movieId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("roomId", roomId);
        model.addAttribute("showtimes", showtimes);

        return "boleto/demo/movie-ticket-plan";
    }

    // API trả danh sách suất chiếu dưới dạng JSON
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<ShowtimeDTO>> getShowtimes(@RequestParam int movieId,
                                                          @RequestParam String startDate,
                                                          @RequestParam(required = false) String startTime,
                                                          @RequestParam(required = false, defaultValue = "0") int roomId) {
        List<ShowtimeDTO> showtimes = showtimeService.getSchedules(movieId, startDate, startTime, roomId);
        return ResponseEntity.ok(showtimes);
    }
}

