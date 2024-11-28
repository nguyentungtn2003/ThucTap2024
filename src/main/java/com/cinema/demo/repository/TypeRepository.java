package com.cinema.demo.repository;

import com.cinema.demo.entity.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<TypeEntity, Integer> {
    // Đây là repository cơ bản để truy vấn thông tin loại phim
}
