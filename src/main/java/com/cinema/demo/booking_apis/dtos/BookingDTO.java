package com.cinema.demo.booking_apis.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private int bookingId;

    private BigDecimal totalFoodTicketAmount;

    private BigDecimal bookingFee;

    private String status;

    private LocalDateTime createdDateTime;
}
