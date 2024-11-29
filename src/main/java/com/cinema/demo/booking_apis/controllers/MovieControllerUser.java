//package com.cinema.demo.booking_apis.controllers;
//
//import com.cinema.demo.booking_apis.dtos.MovieDTO;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.client.RestTemplate;
//
//@Controller
//@RequestMapping("list-movies")
//public class MovieControllerUser {
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    @Value("${server.baseUrl}")
//    private String baseUrl;
//
//    @GetMapping
//    public String displayListMoviePage(Model model) {
//        // URL gọi đến API
//        String apiUrl = baseUrl + "/api/movies/showing";
//
//        // Gửi request để lấy danh sách phim
//        ResponseEntity<MovieDTO[]> response = restTemplate.getForEntity(apiUrl, MovieDTO[].class);
//
//        // Lấy danh sách phim từ response
//        MovieDTO[] movies = response.getBody();
//
//        // Đẩy danh sách phim vào model để Thymeleaf sử dụng
//        model.addAttribute("movies", movies);
//
//        // Trả về tên file Thymeleaf (movie-grid.html)
//        return "boleto/demo/movie-grid";
//    }
//
//}
