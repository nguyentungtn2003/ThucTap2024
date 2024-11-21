package com.cinema.demo.booking_apis.dtos;

import com.cinema.demo.entity.InvoiceEntity;
import com.cinema.demo.entity.TypeOfConcessionEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConcessionOrderDTO {
    private int concessionOrderId;

    private int quantity;

    private BigDecimal price;

    private TypeOfConcessionEntity concessionType;

    private InvoiceEntity invoice;
}
