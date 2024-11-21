package com.cinema.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "ConcessionOrder")
public class ConcessionOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int concessionOrderId;

    private int quantity;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "concessionTypeId")
    private TypeOfConcessionEntity concessionType;

    @ManyToOne
    @JoinColumn(name = "invoiceId")
    private InvoiceEntity invoice;
}
