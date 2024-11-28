package com.cinema.demo.booking_apis.dtos;

import com.cinema.demo.entity.CinemaRoomEntity;
import com.cinema.demo.entity.MovieEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ShowtimeDTO {
    private int showtimeId;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    private CinemaRoomDTO cinemaRoom;

    private MovieDTO movie;
}
