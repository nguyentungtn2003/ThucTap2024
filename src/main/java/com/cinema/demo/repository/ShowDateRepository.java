package com.cinema.demo.repository;

import com.cinema.demo.entity.ShowDateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ShowDateRepository extends JpaRepository<ShowDateEntity, Integer> {
    void deleteByShowtime_ShowtimeId(int showtimeId);
    Optional<ShowDateEntity> findByStartDate(LocalDate date);

}
