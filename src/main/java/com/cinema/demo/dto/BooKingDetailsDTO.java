package com.cinema.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BooKingDetailsDTO {
    private int id;
    private String name;
    private BigDecimal totalAmount;
}
