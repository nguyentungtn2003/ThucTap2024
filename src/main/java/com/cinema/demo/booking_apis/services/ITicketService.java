package com.cinema.demo.booking_apis.services;


import com.cinema.demo.booking_apis.dtos.TicketDTO;
import com.cinema.demo.entity.TicketEntity;

import java.util.List;
import java.util.Optional;

public interface ITicketService {
    List<TicketEntity> getAllTickets();

    List<TicketDTO> getTicketsByUserId(Long userId);

    void saveTicket(TicketDTO ticketDTO);

    TicketDTO getTicketById(int ticketId);

    TicketEntity saveTickets(TicketEntity ticket);
}
