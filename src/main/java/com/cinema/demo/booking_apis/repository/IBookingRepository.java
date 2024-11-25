package com.cinema.demo.booking_apis.repository;

import com.cinema.demo.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookingRepository extends JpaRepository<BookingEntity, Integer> {
}
