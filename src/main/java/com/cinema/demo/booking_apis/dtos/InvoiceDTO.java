package com.cinema.demo.booking_apis.dtos;

import com.cinema.demo.entities.PromotionEntity;
import com.cinema.demo.entities.UserEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class InvoiceDTO {
    private int id;
    private int totalTicketAmount;
    private BigDecimal totalAmount;
    private LocalDateTime createdTime;
    private String status;
    private UserEntity user;
}
