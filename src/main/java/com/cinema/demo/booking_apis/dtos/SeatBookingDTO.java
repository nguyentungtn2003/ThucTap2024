package com.cinema.demo.booking_apis.dtos;

import com.cinema.demo.entities.BookingEntity;
import com.cinema.demo.entities.SeatEntity;
import lombok.Data;

@Data
public class SeatBookingDTO {
    private int id;
    private SeatEntity seat;
    private BookingEntity booking;
}
