package com.cinema.demo.booking_apis.repository;

import com.cinema.demo.entity.CinemaRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ICinemaRoomRepository extends JpaRepository<CinemaRoomEntity, Integer> {

    @Query("SELECT DISTINCT cr FROM CinemaRoomEntity cr " +
            "JOIN ShowtimeEntity st ON cr.id = st.cinemaRoom.id " +
            "JOIN ShowDateEntity sd ON st.showDate.showtimeDateId = sd.showtimeDateId " +
            "WHERE st.movie.movieId = :movieId " +
            "AND sd.date = :startDate " +
            "AND st.startTime = :startTime")
    List<CinemaRoomEntity> getCinemaRoomEntityByMovieAndShowDateAndShowTime(
            @Param("movieId") Integer movieId,
            @Param("startDate") LocalDate startDate,
            @Param("startTime") LocalTime startTime);
    Optional<CinemaRoomEntity> getCinemaRoomEntityById(Integer id);

}