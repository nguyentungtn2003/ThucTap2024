package com.cinema.demo.controller;

import com.cinema.demo.entity.MovieEntity;
import com.cinema.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/home1")
    public String homePage(Model model) {
        List<MovieEntity> movies = movieService.getAllMovies();
        List<MovieEntity> upcomingMovies = movieService.getUpcomingMovies(); // Assuming this method exists

        model.addAttribute("movies", movies);
        model.addAttribute("upcomingMovies", upcomingMovies);

        return "home1";
    }

}

