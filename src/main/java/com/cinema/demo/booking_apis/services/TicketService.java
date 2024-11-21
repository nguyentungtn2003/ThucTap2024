package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.dtos.TicketDTO;
import com.cinema.demo.booking_apis.repositories.ITicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService implements ITicketService{
    @Autowired
    private ITicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<TicketDTO> getTicketsByUserId(Long userId) {
        return ticketRepository.findTicketsByUserId(userId)
                .stream().map(ticket -> modelMapper.map(ticket,TicketDTO.class))
                .collect(Collectors.toList());
    }
}
