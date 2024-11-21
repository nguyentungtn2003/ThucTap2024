package com.cinema.demo.repository;

import com.cinema.demo.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping
public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {
    void deleteByInvoiceEntity_Id(Integer invoiceId);
    List<TicketEntity> findByInvoiceEntity_Id(int invoiceId);
}
