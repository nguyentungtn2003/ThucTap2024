package com.cinema.demo.booking_apis.repositories;

import com.cinema.demo.entities.CinemaRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ICinemaRoomRepository extends JpaRepository<CinemaRoomEntity, Integer> {

    @Query("SELECT cr FROM CinemaRoomEntity cr " +
            "JOIN ShowtimeEntity st ON cr.id = st.cinemaRoom.id " +
            "JOIN ShowDateEntity sd ON st.showtimeId = sd.showtime.showtimeId " +
            "WHERE st.movie.movieId = :movieId " +
            "AND sd.startDate = :startDate " +
            "AND st.startTime = :startTime")
    List<CinemaRoomEntity> getCinemaRoomEntityByMovieAndShowDateAndShowTime(
            @Param("movieId") Integer movieId,
            @Param("startDate") LocalDate startDate,
            @Param("startTime") LocalTime startTime);

}