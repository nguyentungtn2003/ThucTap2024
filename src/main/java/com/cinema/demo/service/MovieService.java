package com.cinema.demo.service;

import com.cinema.demo.entity.MovieEntity;
import com.cinema.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<MovieEntity> getAllMovies() {
        return movieRepository.findAll();
    }
    public List<MovieEntity> getUpcomingMovies() {
        Date currentDate = new Date();
        return movieRepository.findAll().stream()
                .filter(movie -> movie.getReleaseDate() != null && movie.getReleaseDate().after(currentDate))
                .collect(Collectors.toList());
        public MovieEntity getMovieById(int movieId) {
            return movieRepository.findById(movieId)
                    .orElseThrow(() -> new IllegalArgumentException("Movie not found with id: " + movieId));
        }

    }
}
