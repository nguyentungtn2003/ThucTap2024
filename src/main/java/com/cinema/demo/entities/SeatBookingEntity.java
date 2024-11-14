package com.cinema.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Seat_Booking")
public class SeatBookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "seatId")
    private SeatEntity seat;

    @ManyToOne
    @JoinColumn(name = "bookingId")
    private BookingEntity booking;
}
