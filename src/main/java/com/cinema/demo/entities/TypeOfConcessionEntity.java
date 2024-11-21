package com.cinema.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TypeOfConcession")
public class TypeOfConcessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int concessionTypeId;

    private String productType;
}
