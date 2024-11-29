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
            "WHERE st.movie.movieId = :movieId AND st.showDate.startDate = :startDate")
    List<LocalTime> getStartTimeByMovieIdAndStartDate(
            @Param("movieId") Integer movieId,
            @Param("startDate") LocalDate startDate);

    // Lấy danh sách các lịch chiếu (ShowtimeEntity) theo movieId, startDate, startTime và roomId
    @Query("SELECT st FROM ShowtimeEntity st " +
            "WHERE st.movie.movieId = :movieId AND st.showDate.startDate = :startDate " +
            "AND st.startTime = :startTime AND st.cinemaRoom.id = :roomId")
    List<ShowtimeEntity> getSchedulesByMovieIdAndStartDateAndStartTimeAndRoomId(
            @Param("movieId") Integer movieId,
            @Param("startDate") LocalDate startDate,
            @Param("startTime") LocalTime startTime,
            @Param("roomId") Integer roomId);

    // Truy vấn các ngày chiếu (startDate) có sẵn cho bộ phim
    @Query("SELECT DISTINCT st.showDate.startDate FROM ShowtimeEntity st WHERE st.movie.movieId = :movieId ORDER BY st.showDate.startDate ASC")
    List<LocalDate> findAvailableDatesByMovieId(@Param("movieId") Integer movieId);
}