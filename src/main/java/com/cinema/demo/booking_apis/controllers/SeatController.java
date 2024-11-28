package com.cinema.demo.booking_apis.controllers;

import com.cinema.demo.booking_apis.dtos.*;
import com.cinema.demo.booking_apis.mappers.SeatTypeMapper;
import com.cinema.demo.booking_apis.repository.ISeatTypeRepository;
import com.cinema.demo.booking_apis.services.IMovieService;
import com.cinema.demo.entity.SeatTypeEntity;
import com.cinema.demo.entity.ShowtimeEntity;
import com.cinema.demo.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/seat-selection")
public class SeatController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${server.baseUrl}")
    private String baseUrl;

    @Autowired
    private IMovieService movieService;

    private static final String API_GET_SHOW_TIMES = "/api/schedule/schedule-times";
    private static final String API_GET_SEATS = "/api/seats";

    @GetMapping
    public String displaySeatSelectionPage(@RequestParam Integer movieId,
                                           @RequestParam String startDate,
                                           @RequestParam String startTime,
                                           @RequestParam Integer roomId,
                                           HttpServletRequest request,
                                           Model model) {
        HttpSession session = request.getSession();
        // Lưu thông tin vào session
        session.setAttribute("movie_Id", movieId);
        session.setAttribute("start_Date", startDate);
        session.setAttribute("start_Time", startTime);
        session.setAttribute("room_Id", roomId);

        MovieDTO movie = movieService.getById(movieId);
        model.addAttribute("movie", movie);

        // Gọi API để lấy danh sách suất chiếu
        String showTimesUrl = UriComponentsBuilder.fromHttpUrl(baseUrl + API_GET_SHOW_TIMES)
                .queryParam("movieId", movieId)
                .queryParam("startDate", startDate)
                .queryParam("startTime", startTime)
                .queryParam("roomId", roomId)
                .toUriString();

        ResponseEntity<ShowtimeDTO[]> showtimeResponse = restTemplate.exchange(
                showTimesUrl, HttpMethod.GET, null, ShowtimeDTO[].class);

        ShowtimeDTO[] showtimes = showtimeResponse.getBody();
        if (showtimes == null || showtimes.length == 0) {
            model.addAttribute("error", "No showtime found for the selected options.");
            return "boleto/demo/404";
        }

        ShowtimeDTO selectedShowtime = showtimes[0];
        session.setAttribute("showtime", selectedShowtime);
        model.addAttribute("showtime", selectedShowtime);

        model.addAttribute("movieId", movieId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("startTime", startTime);
        model.addAttribute("roomId", roomId);

        // Gọi API để lấy danh sách ghế
        String seatsUrl = UriComponentsBuilder.fromHttpUrl(baseUrl + API_GET_SEATS)
                .queryParam("showtimeId", selectedShowtime.getShowtimeId())
                .toUriString();

        ResponseEntity<SeatDTO[]> seatsResponse = restTemplate.exchange(
                seatsUrl, HttpMethod.GET, null, SeatDTO[].class);

        SeatDTO[] seats = seatsResponse.getBody();
        if (seats == null || seats.length == 0) {
            model.addAttribute("error", "No seats available for the selected showtime.");
            return "boleto/demo/404";
        }


//        // Phân loại ghế theo hàng
//        Map<String, List<SeatDTO>> seatsByRow = new TreeMap<>();
//        for (SeatDTO seat : seats) {
//            String seatPosition = seat.getSeatPosition();
//            if (seatPosition != null && !seatPosition.isEmpty()) {
//                String row = seatPosition.substring(0, 1);
//                seatsByRow.computeIfAbsent(row, k -> new ArrayList<>()).add(seat);
//            }
//        }

        // Phân loại ghế theo hàng và thêm vào LinkedHashMap để giữ thứ tự ngược
        Map<String, List<SeatDTO>> seatsByRow = new LinkedHashMap<>();
        for (char row = 'G'; row >= 'A'; row--) {
            String rowKey = String.valueOf(row);
            List<SeatDTO> rowSeats = Arrays.stream(seats)
                    .filter(seat -> seat.getSeatPosition().startsWith(rowKey))
                    .collect(Collectors.toList());
            seatsByRow.put(rowKey, rowSeats);
        }


        // Loại ghế theo từng hàng
        Map<String, String> seatTypes = new HashMap<>();
        for (char row = 'A'; row <= 'B'; row++) {
            seatTypes.put(String.valueOf(row), "Silver");
        }
        for (char row = 'F'; row <= 'G'; row++) {
            seatTypes.put(String.valueOf(row), "Gold");
        }
        for (char row = 'C'; row <= 'E'; row++) {
            seatTypes.put(String.valueOf(row), "Platinum");
        }

        // Truyền vào model
        model.addAttribute("seatsByRow", seatsByRow);
        model.addAttribute("seatTypes", seatTypes);

        return "boleto/demo/movie-seat-plan";
    }
}