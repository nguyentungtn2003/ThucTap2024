package com.cinema.demo.repository;

import com.cinema.demo.entity.MovieTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieTypeRepositor extends JpaRepository<MovieTypeEntity, Integer> {
    // Đây là repository cơ bản để truy vấn thông tin loại phim
}

