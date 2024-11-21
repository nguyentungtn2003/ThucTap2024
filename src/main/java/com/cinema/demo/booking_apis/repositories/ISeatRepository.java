package com.cinema.demo.booking_apis.repositories;

import com.cinema.demo.entities.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISeatRepository extends JpaRepository<SeatEntity, Integer> {
    List<SeatEntity> getSeatEntityByCinemaRoom_Id(Integer cinemaRoomId);
}