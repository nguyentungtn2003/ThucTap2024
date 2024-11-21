package com.cinema.demo.booking_apis.repository;

import com.cinema.demo.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISeatRepository extends JpaRepository<SeatEntity, Integer> {
    List<SeatEntity> getSeatEntityByCinemaRoom_Id(Integer cinemaRoomId);
}