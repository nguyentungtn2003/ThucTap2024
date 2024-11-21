package com.cinema.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "Showtime")
public class ShowtimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int showtimeId;

    private LocalTime startTime;

    @ManyToOne
    @JoinColumn(name = "cinemaRoomId")
    private CinemaRoomEntity cinemaRoom;

    @ManyToOne
    @JoinColumn(name = "movieId")
    private MovieEntity movie;
}
