package com.cinema.demo.booking_apis.repositories;

import com.cinema.demo.entities.SeatBookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISeatBookingRepository extends JpaRepository<SeatBookingEntity, Integer> {
}
