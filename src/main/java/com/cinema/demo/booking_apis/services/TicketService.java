package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.dtos.MovieDTO;
import com.cinema.demo.booking_apis.dtos.TicketDTO;
import com.cinema.demo.booking_apis.repository.ITicketRepository;
import com.cinema.demo.entity.SeatEntity;
import com.cinema.demo.entity.ShowtimeEntity;
import com.cinema.demo.entity.TicketEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService implements ITicketService{
    @Autowired
    private ITicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ISeatService seatService;

    @Autowired
    private IShowTimeService showtimeService;

    @Override
    public List<TicketEntity> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public List<TicketDTO> getTicketsByUserId(Long userId) {
        return ticketRepository.findTicketsByUserId(userId)
                .stream().map(ticket -> modelMapper.map(ticket,TicketDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void saveTicket(TicketDTO ticketDTO) {
        // Initialize a new TicketEntity
        TicketEntity ticketEntity = new TicketEntity();

        // Set basic fields
        ticketEntity.setPrice(ticketDTO.getPrice());
        ticketEntity.setQrImageURL(ticketDTO.getQrImageURL());
        ticketEntity.setStatus(ticketDTO.getStatus());

        // Fetch and set SeatEntity based on seatId
        if (ticketDTO.getSeat() != null && ticketDTO.getSeat().getSeatId() != 0) {
            SeatEntity seatEntity = seatService.getSeatEntityById(ticketDTO.getSeat().getSeatId());
            if (seatEntity != null) {
                ticketEntity.setSeat(seatEntity);
            } else {
                throw new IllegalArgumentException("Seat with ID " + ticketDTO.getSeat().getSeatId() + " not found.");
            }
        } else {
            throw new IllegalArgumentException("Seat ID is missing in TicketDTO.");
        }

        // Fetch and set ShowtimeEntity based on showtimeId
        if (ticketDTO.getShowtime() != null) {
            ShowtimeEntity showtimeEntity = showtimeService.getShowtimeEntityById(ticketDTO.getShowtime().getShowtimeId());
            if (showtimeEntity != null) {
                ticketEntity.setShowtime(showtimeEntity);
            } else {
                throw new IllegalArgumentException("Showtime with ID " + ticketDTO.getShowtime().getShowtimeId() + " not found.");
            }
        } else {
            throw new IllegalArgumentException("Showtime ID is missing in TicketDTO.");
        }

        // Save the TicketEntity to the database
//        ticketRepository.save(ticketEntity);
        TicketEntity savedTicketEntity = ticketRepository.save(ticketEntity);
        ticketDTO.setTicketId(savedTicketEntity.getTicketId());
    }

    @Override
    public TicketDTO getTicketById(int ticketId) {
        TicketDTO dto = modelMapper.map(ticketRepository.getById(ticketId), TicketDTO.class);
        return dto;
    }

    @Override
    public TicketEntity saveTickets(TicketEntity ticket) {
        return ticketRepository.save(ticket);
    }
}
