package com.cinema.demo.booking_apis.repository;

import com.cinema.demo.booking_apis.dtos.SeatTypeDTO;
import com.cinema.demo.entity.SeatEntity;
import com.cinema.demo.entity.SeatTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISeatTypeRepository extends JpaRepository<SeatTypeEntity, Integer> {
    Optional<SeatTypeEntity> findBySeatName(String seatName);
}
