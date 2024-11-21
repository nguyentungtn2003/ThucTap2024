package com.cinema.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "invoice")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int totalTicketAmount;

    private BigDecimal totalAmount;

    private String status; // PENDING, COMPLETED, FAILED

    private String paymentMethod; // VNPay, Credit Card, etc.

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TicketEntity> tickets; // Đảm bảo có getter và setter cho 'tickets'

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ConcessionOrderEntity> concessionOrders;


    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private PromotionEntity promotion;
}
