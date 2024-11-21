package com.cinema.demo.booking_apis.dtos;

import com.cinema.demo.entity.CinemaRoomEntity;
import com.cinema.demo.entity.MovieEntity;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ShowtimeDTO {
    private int showtimeId;

    private LocalTime startTime;

    private CinemaRoomEntity cinemaRoom;

    private MovieEntity movie;
}
