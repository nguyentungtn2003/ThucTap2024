package com.cinema.demo.repository;

import com.cinema.demo.entity.CinemaRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CinemaRoomRepository extends JpaRepository<CinemaRoomEntity, Integer> {
    CinemaRoomEntity findCinemaRoomEntityById(int idRoom);
}
