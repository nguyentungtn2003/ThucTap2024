package com.cinema.demo.booking_apis.repository;

import com.cinema.demo.entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPromotionRepository extends JpaRepository<PromotionEntity, Integer> {
}
