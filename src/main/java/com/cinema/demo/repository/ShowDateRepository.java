package com.cinema.demo.repository;

import com.cinema.demo.entity.ShowDateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowDateRepository extends JpaRepository<ShowDateEntity, Integer> {
    void deleteByShowtime_ShowtimeId(int showtimeId);

}
