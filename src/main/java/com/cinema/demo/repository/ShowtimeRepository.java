package com.cinema.demo.repository;

import com.cinema.demo.entity.ShowtimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
public interface ShowtimeRepository extends JpaRepository<ShowtimeEntity, Integer> {

    Optional<ShowtimeEntity> findById(int showtimeId);;
    @Transactional
    @Modifying
    @Query("DELETE FROM ShowtimeEntity s WHERE s.movie.movieId = :movieId")
    void deleteByMovieId(@Param("movieId") int movieId);
}
