package com.cinema.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Booking")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    private BigDecimal totalFoodTicketAmount;

    private BigDecimal bookingFee;

    private String status;

    private LocalDateTime createdDateTime;
}
