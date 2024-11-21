package com.cinema.demo.repository;

import com.cinema.demo.entity.ShowtimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowtimeRepository extends JpaRepository<ShowtimeEntity, Integer> {

    Optional<ShowtimeEntity> findById(int showtimeId);;
}
