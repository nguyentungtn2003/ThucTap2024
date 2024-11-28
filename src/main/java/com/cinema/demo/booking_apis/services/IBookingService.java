package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.dtos.BookingDTO;

public interface IBookingService {
    void saveBooking(BookingDTO bookingDTO);

    BookingDTO getBookingById(int bookingId);

    // Đảm bảo rằng việc cập nhật được thực hiện trong một transaction
    void updateBooking(BookingDTO bookingDTO);
}
