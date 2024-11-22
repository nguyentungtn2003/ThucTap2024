package com.cinema.demo.booking_apis.services;


import com.cinema.demo.booking_apis.dtos.MovieDTO;
import com.cinema.demo.booking_apis.repository.IMovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService implements IMovieService{

    @Autowired
    private IMovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<MovieDTO> findAllShowingMovies() {
        return movieRepository.findMovieEntityByRunningTimeOrderByMovieIdDesc(1)
                .stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<MovieDTO> findAllShowingMovies() {
//        return movieRepository.findMovieEntityByRunningTimeOrderByMovieIdDesc(1)
//                .stream()
//                .map(movie -> {
//                    MovieDTO dto = modelMapper.map(movie, MovieDTO.class);
//                    // Lấy thể loại của phim
//                    List<String> types = movieRepository.findTypesByMovieId(movie.getMovieId())
//                            .stream()
//                            .map(type -> type.getTypeName())
//                            .collect(Collectors.toList());
//                    dto.setTypes(types);
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }


//    @Override
//    public MovieDTO getById(Integer movieId) {
//        return modelMapper.map(movieRepository.getById(movieId),MovieDTO.class);
//    }

    @Override
    public MovieDTO getById(Integer movieId) {
        MovieDTO dto = modelMapper.map(movieRepository.getById(movieId), MovieDTO.class);
        // Lấy thể loại của phim
        List<String> types = movieRepository.findTypesByMovieId(movieId)
                .stream()
                .map(type -> type.getTypeName())
                .collect(Collectors.toList());
        dto.setTypes(types);
        return dto;
    }


    @Override
    public List<MovieDTO> findAllShowingMoviesByName(String keyword) {
        return movieRepository.findMovieEntityByRunningTimeAndTitleContaining(1,keyword)
                .stream().map(movie -> modelMapper.map(movie,MovieDTO.class))
                .collect(Collectors.toList());
    }
}
