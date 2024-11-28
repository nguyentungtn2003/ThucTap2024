package com.cinema.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

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

    @OneToMany(mappedBy = "showtime")  // 'showtime' là tên thuộc tính trong SeatEntity
    private List<SeatEntity> seats;  // Thêm thuộc tính seats

    @OneToMany(mappedBy = "showtime")
    @JsonManagedReference  // Add this annotation to avoid infinite recursion
    private List<ShowDateEntity> showDates;
}
