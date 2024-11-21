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

    private String status; // PENDING, COMPLETED, FAILED

    private String paymentMethod; // VNPay, Credit Card, etc.

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

//    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<TicketEntity> tickets; // Đảm bảo có getter và setter cho 'tickets'
//
//    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<ConcessionOrderEntity> concessionOrders;
//
//
//    @ManyToOne
//    @JoinColumn(name = "promotion_id")
//    private PromotionEntity promotion;
}
