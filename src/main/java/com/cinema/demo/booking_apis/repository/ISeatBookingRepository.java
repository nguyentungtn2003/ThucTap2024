package com.cinema.demo.booking_apis.repository;

import com.cinema.demo.entity.SeatBookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ISeatBookingRepository extends JpaRepository<SeatBookingEntity, Integer> {
    List<SeatBookingEntity> findByBooking_BookingId(Integer seatId);
}
