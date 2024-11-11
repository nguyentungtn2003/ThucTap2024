package com.cinema.demo.service;

import com.cinema.demo.dto.MovieDTO;

import java.util.List;

public interface IMovieService {
    List<MovieDTO> getAllMovies();
    List<MovieDTO> getUpcomingMovies();
    List<MovieDTO> getMoviesToday();
}
