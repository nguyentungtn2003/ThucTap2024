package com.cinema.demo.booking_apis.repositories;

import com.cinema.demo.entities.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvoiceRepository extends JpaRepository<InvoiceEntity, Integer> {
}