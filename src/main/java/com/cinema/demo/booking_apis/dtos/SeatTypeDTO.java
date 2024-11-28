package com.cinema.demo.booking_apis.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SeatTypeDTO {
    private int seatTypeId;

    private String seatName;

    private BigDecimal seatPrice;
}
