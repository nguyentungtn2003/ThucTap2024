package com.cinema.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Seat")
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatId;

    private String seatPosition;

    @ManyToOne
    @JoinColumn(name = "cinemaRoomId")
    private CinemaRoomEntity cinemaRoom;

    @ManyToOne
    @JoinColumn(name = "seatTypeId")
    private SeatTypeEntity seatType;  // Đây là thuộc tính seatType

    @ManyToOne
    @JoinColumn(name = "showtimeId")
    private ShowtimeEntity showtime;
}
