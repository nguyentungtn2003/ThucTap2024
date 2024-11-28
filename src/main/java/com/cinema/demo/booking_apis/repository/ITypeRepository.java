package com.cinema.demo.booking_apis.repository;

import com.cinema.demo.entity.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITypeRepository extends JpaRepository<TypeEntity, Integer> {
}
