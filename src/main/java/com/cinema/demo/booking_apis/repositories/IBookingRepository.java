package com.cinema.demo.booking_apis.repositories;

import com.cinema.demo.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookingRepository extends JpaRepository<BookingEntity, Integer> {
}
