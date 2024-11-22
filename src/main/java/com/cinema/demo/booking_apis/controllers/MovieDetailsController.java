package com.cinema.demo.booking_apis.controllers;

import com.cinema.demo.booking_apis.dtos.MovieDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("movie-details")
public class MovieDetailsController {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${server.baseUrl}")
    private String baseUrl;

    /**
     * Hiển thị trang chi tiết phim
     * @param movieId ID của bộ phim cần xem chi tiết
     * @param model   Model để truyền dữ liệu tới view
     * @return Tên template Thymeleaf để hiển thị (movie-details.html)
     */
    @GetMapping
    public String displayMovieDetailPage(@RequestParam Integer movieId, Model model) {
        // Tạo URL endpoint từ cấu hình base URL
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUrl + "/api/movies/details")
                .queryParam("movieId", "{movieId}")
                .encode()
                .toUriString();

        // Tham số cho request
        Map<String, Integer> params = new HashMap<>();
        params.put("movieId", movieId);

        // Gửi request đến backend để lấy thông tin phim
        ResponseEntity<MovieDTO> response = restTemplate.getForEntity(urlTemplate, MovieDTO.class, params);
        MovieDTO movie = response.getBody();

        // Truyền dữ liệu phim vào model để Thymeleaf render
        model.addAttribute("movie", movie);

        // Trả về tên view Thymeleaf
        return "boleto/demo/movie-details";
    }
}
