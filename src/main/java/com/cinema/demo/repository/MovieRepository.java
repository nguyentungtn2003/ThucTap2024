package com.cinema.demo.repository;

import com.cinema.demo.dto.MovieDTO;
import com.cinema.demo.entity.BookingEntity;
import com.cinema.demo.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
    //    @Query(value = "SELECT m.movie_id AS movieId, m.title AS title, m.description AS description, " +
//            "GROUP_CONCAT(t.type_name SEPARATOR ', ') AS types " +
//            "FROM Movie m " +
//            "JOIN Movie_Type mt ON m.movie_id = mt.movie_id " +
//            "JOIN Type t ON mt.type_id = t.type_id " +
//            "GROUP BY m.movie_id, m.title, m.description", nativeQuery = true)
//    List<MovieDTO> getAllMoviesWithType();
    List<MovieDTO> findByTitleContainingIgnoreCase(String title); // Tìm phim theo title
    List<MovieEntity> findAllByReleaseDateBetween(Date createdDateTime, Date endDateTime);
}
