package com.cinema.demo.booking_apis.dtos;

import com.cinema.demo.entity.UserEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoiceDTO {
    private int id;
    private int totalTicketAmount;
    private BigDecimal totalAmount;
    private LocalDateTime createdTime;
    private String status;
    private UserEntity user;
}
