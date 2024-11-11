package com.cinema.demo.service.impl;

import com.cinema.demo.dto.MovieDTO;
import com.cinema.demo.entity.MovieEntity;
import com.cinema.demo.repository.MovieRepository;
import com.cinema.demo.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements IMovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getUpcomingMovies() {
        Date currentDate = new Date();
        return movieRepository.findAll().stream()
                .filter(movie -> movie.getReleaseDate() != null && movie.getReleaseDate().after(currentDate))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMoviesToday() {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfDay = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endOfDay = calendar.getTime();

        return movieRepository.findAll().stream()
                .filter(movie -> movie.getReleaseDate() != null
                        && !movie.getReleaseDate().before(startOfDay)
                        && movie.getReleaseDate().before(endOfDay))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Inline conversion method to map MovieEntity to MovieDTO
    private MovieDTO convertToDTO(MovieEntity entity) {
        MovieDTO dto = new MovieDTO();
        dto.setMovieId(entity.getMovieId());
        dto.setDescription(entity.getDescription());
        dto.setProductionCompany(entity.getProductionCompany());
        dto.setDirector(entity.getDirector());
        dto.setTitle(entity.getTitle());
        dto.setImage(entity.getImage());
        dto.setTrailerUrl(entity.getTrailerurl());
        dto.setVersion(entity.getVersion());
        dto.setActor(entity.getActor());
        dto.setReleaseDate(entity.getReleaseDate());
        dto.setRunningTime(entity.getRunningTime());
        dto.setIsShowing(entity.getIs_showing());
        return dto;
    }
}
