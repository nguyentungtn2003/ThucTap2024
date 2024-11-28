package com.cinema.demo.booking_apis.services;


import com.cinema.demo.booking_apis.dtos.SeatDTO;
import com.cinema.demo.entity.SeatEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ISeatService {
    List<SeatDTO> getSeatsByShowTimeId(Integer showtimeId);
    SeatDTO getSeatById(Integer seatId);
    SeatEntity getSeatEntityById(Integer seatId);
    void updateSeatStatus(SeatDTO seatDTO);

    BigDecimal getPriceBySeatType(String seatName);
}
