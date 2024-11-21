package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.dtos.BookingRequestDTO;

public interface IInvoiceService {
    void createNewInvoice(BookingRequestDTO bookingRequestDTO) throws RuntimeException;
}
