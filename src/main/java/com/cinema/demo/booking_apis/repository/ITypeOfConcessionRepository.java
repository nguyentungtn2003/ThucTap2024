package com.cinema.demo.booking_apis.repository;

import com.cinema.demo.entity.ConcessionOrderEntity;
import com.cinema.demo.entity.TypeOfConcessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITypeOfConcessionRepository extends JpaRepository<TypeOfConcessionEntity, Integer> {
}
