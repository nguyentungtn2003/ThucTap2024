package com.cinema.demo.controller;

import com.cinema.demo.entity.MovieEntity;
import com.cinema.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/{movieId}")
    public String getMovieDetails(@PathVariable int movieId, Model model) {
        MovieEntity movie = movieService.getMovieById(movieId);
        model.addAttribute("movie", movie);
        return "movie_detail";
    }
}
