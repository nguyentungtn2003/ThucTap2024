package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.dtos.BookingRequestDTO;
import com.cinema.demo.entity.InvoiceEntity;

import java.util.Optional;

public interface IInvoiceService {
    void createNewInvoice(BookingRequestDTO bookingRequestDTO) throws RuntimeException;

    // Phương thức lấy hóa đơn theo ID
    Optional<InvoiceEntity> getInvoiceById(int invoiceId);
}
