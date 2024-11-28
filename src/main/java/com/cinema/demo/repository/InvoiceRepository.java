package com.cinema.demo.repository;

import com.cinema.demo.dto.InvoiceTicketDTO;
import com.cinema.demo.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Integer> , InvoiceRepositoryCustom{
    List<InvoiceEntity> findByUserId(int userId);
}
