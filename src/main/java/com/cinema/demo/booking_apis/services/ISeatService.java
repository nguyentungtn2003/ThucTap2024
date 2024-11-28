package com.cinema.demo.booking_apis.services;


import com.cinema.demo.booking_apis.dtos.SeatDTO;

import java.util.List;

public interface ISeatService {
    List<SeatDTO> getSeatsByShowTimeId(Integer showtimeId);
}
