package com.cinema.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

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

    private Date createdDateTime;
}
