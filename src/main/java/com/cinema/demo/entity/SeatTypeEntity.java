package com.cinema.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "SeatType")
public class SeatTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatTypeId;

    private String seatName;

    private BigDecimal seatPrice;
}
