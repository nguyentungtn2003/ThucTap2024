package com.cinema.demo.booking_apis.services;


import com.cinema.demo.booking_apis.dtos.TicketDTO;

import java.util.List;

public interface ITicketService {
    List<TicketDTO> getTicketsByUserId(Long userId);
}
