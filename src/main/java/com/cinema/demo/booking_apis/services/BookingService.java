package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.dtos.BookingDTO;
import com.cinema.demo.booking_apis.repository.IBookingRepository;
import com.cinema.demo.entity.BookingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService implements IBookingService{
    @Autowired
    private IBookingRepository bookingRepository;

    @Override
    public void saveBooking(BookingDTO bookingDTO) {
        BookingEntity bookingEntity = new BookingEntity();
        // Chuyển đổi BookingDTO thành BookingEntity và lưu vào DB
        bookingRepository.save(bookingEntity);
    }

    @Override
    public BookingDTO getBookingById(int bookingId) {
        BookingEntity bookingEntity = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        // Chuyển đổi BookingEntity thành BookingDTO
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(bookingEntity.getBookingId());
        bookingDTO.setTotalFoodTicketAmount(bookingEntity.getTotalFoodTicketAmount());
        bookingDTO.setBookingFee(bookingEntity.getBookingFee());
        bookingDTO.setStatus(bookingEntity.getStatus());
        bookingDTO.setCreatedDateTime(bookingEntity.getCreatedDateTime());

        return bookingDTO;
    }

    @Override // Đảm bảo rằng việc cập nhật được thực hiện trong một transaction
    public void updateBooking(BookingDTO bookingDTO) {
        BookingEntity bookingEntity = bookingRepository.findById(bookingDTO.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingDTO.getBookingId()));

        // Cập nhật thông tin booking
        bookingEntity.setStatus(bookingDTO.getStatus());
        bookingEntity.setTotalFoodTicketAmount(bookingDTO.getTotalFoodTicketAmount());
        bookingEntity.setBookingFee(bookingDTO.getBookingFee());
        bookingEntity.setCreatedDateTime(bookingDTO.getCreatedDateTime());

        // Lưu lại thông tin đã cập nhật
        bookingRepository.save(bookingEntity);
    }

}
