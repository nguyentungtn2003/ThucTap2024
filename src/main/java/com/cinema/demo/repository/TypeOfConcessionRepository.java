package com.cinema.demo.repository;

import com.cinema.demo.entity.TypeOfConcessionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeOfConcessionRepository extends JpaRepository<TypeOfConcessionEntity, Integer> {
    Page<TypeOfConcessionEntity> findByProductTypeContainingIgnoreCase(String productType, Pageable pageable);
}
