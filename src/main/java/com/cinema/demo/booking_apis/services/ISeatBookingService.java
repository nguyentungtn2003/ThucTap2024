package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.dtos.SeatBookingDTO;
import com.cinema.demo.booking_apis.dtos.SeatDTO;

import java.util.List;

public interface ISeatBookingService {
    void saveSeatBooking(SeatBookingDTO seatBookingDTO);

    List<SeatBookingDTO> getSeatsByBookingId(int bookingId);
}
