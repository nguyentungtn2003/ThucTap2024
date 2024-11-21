package com.cinema.demo.booking_apis.repositories;

import com.cinema.demo.entities.ConcessionOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConcessionOrderRepository extends JpaRepository<ConcessionOrderEntity, Integer> {

}
