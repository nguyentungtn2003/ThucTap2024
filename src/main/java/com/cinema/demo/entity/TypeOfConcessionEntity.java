package com.cinema.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TypeOfConcession")
public class TypeOfConcessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  concessionTypeId;

    private String productType;
    private int price;
    private  int quantity;

}
