package com.cinema.demo.booking_apis.controllers;

import com.cinema.demo.booking_apis.dtos.ShowDateDTO;
import com.cinema.demo.booking_apis.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${server.baseUrl}")
    private String baseUrl;

    @Autowired
    private RoomService roomService;

    @GetMapping
    public String displaySchedulePage(@RequestParam int movieId, Model model) {
        String apiUrl = baseUrl + "/api/schedule/dates?movieId=" + movieId;

        ResponseEntity<ShowDateDTO[]> response = restTemplate.getForEntity(apiUrl, ShowDateDTO[].class);
        List<ShowDateDTO> showDates = List.of(response.getBody());

        model.addAttribute("movieId", movieId);
        model.addAttribute("showDates", showDates);

        return "boleto/demo/movie-ticket-plan";
    }
}
