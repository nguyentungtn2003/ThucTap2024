package com.cinema.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "Showtime")
public class ShowtimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int showtimeId;

    @ManyToOne
    @JoinColumn(name = "cinemaRoomId")
    private CinemaRoomEntity cinemaRoom;

    @ManyToOne
    @JoinColumn(name = "movieId")
    private MovieEntity movie;

    @OneToMany(mappedBy = "showtime")  // 'showtime' là tên thuộc tính trong SeatEntity
    private List<SeatEntity> seats;  // Thêm thuộc tính seats
}
