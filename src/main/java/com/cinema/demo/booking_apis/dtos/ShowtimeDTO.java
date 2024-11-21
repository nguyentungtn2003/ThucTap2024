package com.cinema.demo.booking_apis.dtos;

import com.cinema.demo.entities.CinemaRoomEntity;
import com.cinema.demo.entities.MovieEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ShowtimeDTO {
    private int showtimeId;

    private LocalTime startTime;

    private CinemaRoomEntity cinemaRoom;

    private MovieEntity movie;
}
