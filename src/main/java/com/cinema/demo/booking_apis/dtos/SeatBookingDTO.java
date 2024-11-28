package com.cinema.demo.booking_apis.dtos;

import com.cinema.demo.entity.BookingEntity;
import com.cinema.demo.entity.SeatEntity;
import lombok.Data;

@Data
public class SeatBookingDTO {
    private int id;
    private SeatDTO seat;
    private BookingDTO booking;
}
