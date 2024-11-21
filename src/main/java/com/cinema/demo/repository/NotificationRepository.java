package com.cinema.demo.repository;

import com.cinema.demo.entity.NotificationEntity;
import com.cinema.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByUserAndIsReadFalse(UserEntity user);
}
