package com.cinema.demo.booking_apis.repository;

import com.cinema.demo.entity.SeatBookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISeatBookingRepository extends JpaRepository<SeatBookingEntity, Integer> {
}
