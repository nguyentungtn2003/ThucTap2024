package com.cinema.demo.booking_apis.controllers;

import com.cinema.demo.booking_apis.dtos.MovieDTO;
import com.cinema.demo.booking_apis.dtos.ShowDateDTO;
import com.cinema.demo.booking_apis.dtos.ShowtimeDTO;
import com.cinema.demo.booking_apis.services.IMovieService;
import com.cinema.demo.booking_apis.services.RoomService;
import com.cinema.demo.booking_apis.services.ShowTimeService;
import com.cinema.demo.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
//@RequestMapping("/schedule")
//public class ScheduleController {
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    @Value("${server.baseUrl}")
//    private String baseUrl;
//
//    @Autowired
//    private RoomService roomService;
//
//    @GetMapping
//    public String displaySchedulePage(@RequestParam int movieId, Model model) {
//        String apiUrl = baseUrl + "/api/schedule/dates?movieId=" + movieId;
//
//        try {
//            ResponseEntity<ShowDateDTO[]> response = restTemplate.getForEntity(apiUrl, ShowDateDTO[].class);
//            ShowDateDTO[] showDatesArray = response.getBody();
//
//            if (showDatesArray != null) {
//                List<ShowDateDTO> startDate = List.of(showDatesArray);
//                model.addAttribute("startDate", startDate);
//            } else {
//                model.addAttribute("showDates", List.of());
//            }
//        } catch (Exception e) {
//            // Add error handling
//            model.addAttribute("error", "Could not retrieve show dates. Please try again later.");
//        }
//
//        model.addAttribute("movieId", movieId);
//
//        return "boleto/demo/movie-ticket-plan";
//    }
//
//}

@Controller
@RequestMapping("/ticket-plan")
public class ScheduleController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private IMovieService movieService;

    @Autowired
    private ShowTimeService showtimeService;

    @Value("${server.baseUrl}")
    private String baseUrl;

    private static final String API_GET_START_TIMES = "/api/schedule/start-times";
//    private static final String API_GET_SHOW_TIMES = "/api/schedule/schedule-times";

    @GetMapping
    public String showTicketPlanPage(@RequestParam Integer movieId,
                                     @RequestParam(required = false) String startDate,
                                     Model model) {

        MovieDTO movie = movieService.getById(movieId);
        model.addAttribute("movie", movie);

//        model.addAttribute("movie", movie);

        if (startDate == null || startDate.isEmpty()) {
            List<LocalDate> availableDates = showtimeService.getAvailableDates(movieId);
            model.addAttribute("availableDates", availableDates);
            model.addAttribute("movieId", movieId);
            return "boleto/demo/movie-ticket-plan";
        }

        String startTimesUrl = baseUrl + API_GET_START_TIMES + "?movieId=" + movieId + "&startDate=" + startDate;
        ResponseEntity<String[]> startTimesResponse = restTemplate.exchange(startTimesUrl, HttpMethod.GET, null, String[].class);
        String[] startTimes = startTimesResponse.getBody();

//        String showtimesUrl = baseUrl + API_GET_SHOW_TIMES + "?movieId=" + movieId + "&startDate=" + startDate;
//        ResponseEntity<List<ShowtimeDTO>> showtimesResponse = restTemplate.exchange(showtimesUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<ShowtimeDTO>>() {});
//        List<ShowtimeDTO> showtimes = showtimesResponse.getBody();

        model.addAttribute("movieId", movieId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("startTimes", startTimes);
//        model.addAttribute("showtimes", showtimes);

        return "boleto/demo/movie-ticket-plan";
    }

    @PostMapping("/select-seat")
    public String selectSeat(@RequestParam Integer movieId, @RequestParam String startDate,
                             @RequestParam String startTime, @RequestParam Integer roomId, Model model) {

        // Truyền dữ liệu qua Model cho trang chọn ghế
        model.addAttribute("movieId", movieId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("startTime", startTime);
        model.addAttribute("roomId", roomId);

        return "boleto/demo/movie-seat-plan";
    }
}





