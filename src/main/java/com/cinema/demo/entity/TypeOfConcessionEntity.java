package com.cinema.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "TypeOfConcession")
public class TypeOfConcessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int concessionTypeId;

    private String productType;

    private BigDecimal price;
}
