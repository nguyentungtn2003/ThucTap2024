package com.cinema.demo.repository;

import com.cinema.demo.entity.CinemaRoomDetailEntity;
import com.cinema.demo.entity.CinemaRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CinemaRoomDetailRepository extends JpaRepository<CinemaRoomDetailEntity, Integer> {
    List<CinemaRoomDetailEntity> findAllByRoom(CinemaRoomEntity cinemaRoom);
}
