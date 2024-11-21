package com.cinema.demo.booking_apis.repositories;

import com.cinema.demo.entities.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPromotionRepository extends JpaRepository<PromotionEntity, Integer> {
}
