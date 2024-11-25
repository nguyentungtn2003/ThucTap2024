package com.cinema.demo.booking_apis.repository;

import com.cinema.demo.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IMovieRepository extends JpaRepository<MovieEntity, Integer> {
    List<MovieEntity> findMovieEntityByRunningTimeOrderByMovieIdDesc(Integer runningTime);
    List<MovieEntity> findMovieEntityByRunningTimeAndTitleContaining(Integer runningTime,String title);
    @Query("SELECT mt.movie FROM MovieTypeEntity mt WHERE mt.type.typeId = :typeId")
    List<MovieEntity> findMoviesByTypeId(@Param("typeId") Integer typeId);

}