package com.cinema.demo.controller;

import com.cinema.demo.dto.MovieDTO;
import com.cinema.demo.entity.MovieEntity;
import com.cinema.demo.service.impl.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

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

}

