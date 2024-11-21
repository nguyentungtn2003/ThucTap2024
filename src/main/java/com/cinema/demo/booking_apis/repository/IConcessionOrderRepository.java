package com.cinema.demo.booking_apis.repository;

import com.cinema.demo.entity.ConcessionOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConcessionOrderRepository extends JpaRepository<ConcessionOrderEntity, Integer> {

}
