package com.cinema.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Invoice")
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int totalTicketAmount;

    private BigDecimal totalAmount;

    @CreatedDate
    private LocalDateTime createdTime;

    private String status;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;
}
