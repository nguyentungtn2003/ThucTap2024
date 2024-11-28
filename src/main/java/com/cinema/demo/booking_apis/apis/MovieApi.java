package com.cinema.demo.booking_apis.apis;

import com.cinema.demo.booking_apis.dtos.MovieDTO;
import com.cinema.demo.booking_apis.repository.IMovieRepository;
import com.cinema.demo.booking_apis.services.IMovieService;
import com.cinema.demo.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping(value="/api/movies", produces = "application/json")
public class MovieApi {
    @Autowired
    private IMovieService movieService;

    @Autowired
    private IMovieRepository movieRepository;

    @GetMapping("/showing")
    public ResponseEntity<List<MovieDTO>> findAllShowingMovies(){
        return new ResponseEntity<>(movieService.findAllShowingMovies(), HttpStatus.OK);
    }

    @GetMapping("/details")
    public MovieDTO getMovieById(@RequestParam Integer movieId){
        return movieService.getById(movieId);
    }

    @GetMapping("/showing/search")
    public List<MovieDTO> findAllShowingMoviesByName(@RequestParam String name){
        return movieService.findAllShowingMoviesByName(name);
    }

    @PostMapping
    public void updateMovie(@RequestBody MovieEntity movie){
        movieRepository.save(movie);
    }

    @GetMapping("/{movieId}/types")
    public ResponseEntity<List<String>> findTypesByMovieId(@PathVariable int movieId) {
        List<String> types = movieRepository.findTypesByMovieId(movieId)
                .stream()
                .map(type -> type.getTypeName()) // Chỉ lấy tên thể loại
                .collect(Collectors.toList());
        return new ResponseEntity<>(types, HttpStatus.OK);
    }
}
