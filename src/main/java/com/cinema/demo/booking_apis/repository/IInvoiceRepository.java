package com.cinema.demo.booking_apis.repository;

import com.cinema.demo.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvoiceRepository extends JpaRepository<InvoiceEntity, Integer> {
}