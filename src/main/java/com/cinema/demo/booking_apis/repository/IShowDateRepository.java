package com.cinema.demo.booking_apis.repository;

import com.cinema.demo.entity.ShowDateEntity;
import com.cinema.demo.entity.ShowtimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IShowDateRepository extends JpaRepository<ShowDateEntity, Integer> {
    List<ShowDateEntity> findByStartDate(LocalDate startDate);

    @Query("SELECT DISTINCT sd FROM ShowDateEntity sd " +
            "JOIN sd.showtime st " +
            "WHERE st.movie.movieId = :movieId")
    List<ShowDateEntity> findByMovieId(@Param("movieId") int movieId);
}
