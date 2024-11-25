package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.dtos.MovieDTO;

import java.util.List;

public interface IMovieService {
    List<MovieDTO> findAllShowingMovies();
    MovieDTO getById(Integer movieId);
    List<MovieDTO> findAllShowingMoviesByName(String name);
}
