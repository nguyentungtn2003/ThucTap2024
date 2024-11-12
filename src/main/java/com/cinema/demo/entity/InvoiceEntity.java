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

    @OneToMany(mappedBy = "invoice")  // Thay đổi quan hệ từ ManyToOne sang OneToMany
    private List<TicketEntity> tickets; // Thêm danh sách vé

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private PromotionEntity promotion;
}
