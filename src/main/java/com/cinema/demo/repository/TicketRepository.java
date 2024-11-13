package com.cinema.demo.repository;

import com.cinema.demo.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {
    void deleteByInvoiceId(Integer invoiceId);
    List<TicketEntity> findByInvoiceId(int invoiceId);
}
