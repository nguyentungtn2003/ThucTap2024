package com.cinema.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "Invoice")
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int totalTicketAmount;

    private BigDecimal totalAmount;

    private String status;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<TicketEntity> tickets;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConcessionOrderEntity> concessionOrders;


    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private PromotionEntity promotion;
}
