package com.cinema.demo.booking_apis.repository;

import com.cinema.demo.entity.ShowtimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IShowTimeRepository extends JpaRepository<ShowtimeEntity, Integer> {
    // Lấy danh sách giờ chiếu (startTime) theo movieId và startDate
    @Query("SELECT DISTINCT st.startTime FROM ShowtimeEntity st " +
            "JOIN ShowDateEntity sd ON st.showtimeId = sd.showtime.showtimeId " +
            "WHERE st.movie.movieId = :movieId AND sd.date = :startDate")
    List<LocalTime> getStartTimeByMovieIdAndStartDate(
            @Param("movieId") Integer movieId,
            @Param("startDate") LocalDate startDate);

    // Lấy danh sách các lịch chiếu (ShowtimeEntity) theo movieId, startDate, startTime và roomId
    @Query("SELECT st FROM ShowtimeEntity st " +
            "JOIN ShowDateEntity sd ON st.showtimeId = sd.showtime.showtimeId " +
            "WHERE st.movie.movieId = :movieId AND sd.date = :startDate " +
            "AND st.startTime = :startTime AND st.cinemaRoom.id = :roomId")
    List<ShowtimeEntity> getSchedulesByMovieIdAndStartDateAndStartTimeAndRoomId(
            @Param("movieId") Integer movieId,
            @Param("startDate") LocalDate startDate,
            @Param("startTime") LocalTime startTime,
            @Param("roomId") Integer roomId);
}