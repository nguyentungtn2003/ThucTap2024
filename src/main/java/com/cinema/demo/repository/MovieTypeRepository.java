package com.cinema.demo.repository;

import com.cinema.demo.entity.MovieTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieTypeRepository extends JpaRepository<MovieTypeEntity, Integer> {

}

