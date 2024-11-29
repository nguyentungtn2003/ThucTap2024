package com.cinema.demo.controller;

import com.cinema.demo.dto.MovieDTO;
import com.cinema.demo.entity.MovieEntity;
import com.cinema.demo.service.impl.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private MovieServiceImpl movieService;

    @GetMapping("/home1")
    public String homePage(Model model) {
        List<MovieDTO> movies = movieService.getAllMoviesIsShowing();
        List<MovieDTO> upcomingMovies = movieService.getUpcomingMovies(); // Assuming this method exists

        model.addAttribute("movies", movies);
        model.addAttribute("upcomingMovies", upcomingMovies);

        return "home1";
    }

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${server.baseUrl}")
    private String baseUrl;

    @GetMapping("list-movies")
    public String getMovies(@RequestParam(value = "entriesPerPage", defaultValue = "20") int entriesPerPage,
                            @RequestParam(value = "page", defaultValue = "1") int page,
                            Model model,Authentication authentication) {
        // Lấy danh sách phim với phân trang
        Page<MovieDTO> movieDTOPage = movieService.getMoviesPage(entriesPerPage, page);
        model.addAttribute("movie", new MovieDTO());

        // Check login status
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("username", authentication.getName());
        } else {
            model.addAttribute("isLoggedIn", false);
        }

        // Thêm dữ liệu vào model để hiển thị trên view
        model.addAttribute("movies", movieDTOPage.getContent());  // Danh sách các MovieDTO
        System.out.println( movieDTOPage.getContent());
        model.addAttribute("totalPages", movieDTOPage.getTotalPages());  // Tổng số trang
        model.addAttribute("currentPage", page);  // Trang hiện tại

        return "boleto/demo/movie-grid";
    }

//    @GetMapping("movie-details")
//    public String displayMovieDetailPage(@RequestParam Integer movieId, Model model, Authentication authentication) {
//        // Tạo URL endpoint từ cấu hình base URL
//        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUrl + "/api/movies/details")
//                .queryParam("movieId", "{movieId}")
//                .encode()
//                .toUriString();
//
//        // Tham số cho request
//        Map<String, Integer> params = new HashMap<>();
//        params.put("movieId", movieId);
//
//        // Gửi request đến backend để lấy thông tin phim
//        MovieDTO movie = movieService.getById(movieId);
//        model.addAttribute("movie", movie);
//        if (authentication != null && authentication.isAuthenticated()) {
//            model.addAttribute("isLoggedIn", true);
//            model.addAttribute("username", authentication.getName());
//        } else {
//            model.addAttribute("isLoggedIn", false);
//        }
//
//        // Truyền dữ liệu phim vào model để Thymeleaf render
//        model.addAttribute("movie", movie);
//
//        // Trả về tên view Thymeleaf
//        return "boleto/demo/movie-details";
//    }

}

