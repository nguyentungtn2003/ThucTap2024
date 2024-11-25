package com.cinema.demo.service;

import com.cinema.demo.dto.MovieDTO;
import com.cinema.demo.entity.MovieEntity;
import com.cinema.demo.entity.MovieTypeEntity;
import com.cinema.demo.entity.TypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IMovieService {
    List<MovieDTO> getAllMovies();
    List<MovieDTO> getAllMoviesIsShowing();
    List<MovieDTO> getUpcomingMovies();
    List<MovieDTO> getMoviesToday();
    MovieDTO getById(Integer movieId);
    MovieDTO createMovie(MovieDTO movieDTO);
    MovieDTO updateMovie(MovieDTO movieDTO);

    void saveMovieType(MovieTypeEntity movieTypeEntity);

//    void deleteMovieTypesByMovieId(int movieId);

    void deleteMovie(int movieId);
    String uploadImage(MultipartFile file);
    Page<MovieDTO> getAllMovies(Pageable pageable) ;
    //List<MovieDTO> getAllMoviesWithType();
    MovieEntity save(MovieEntity movie);
    Optional<MovieEntity> findById(Integer movieId);
    List<TypeEntity> getTypeMoVie();
    TypeEntity getTypeById(Integer typeId);

    List<MovieDTO> searchMoviesByTitle(String search);

    Page<MovieDTO> getMoviesPage(int entriesPerPage, int page);
    //  List<MovieDTO> findAllShowingMoviesByName(String name);
}
