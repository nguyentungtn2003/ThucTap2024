package com.cinema.demo.service;

import com.cinema.demo.dto.TicketConfirmationDTO;
import com.cinema.demo.entity.InvoiceEntity;
import com.cinema.demo.entity.NotificationEntity;
import com.cinema.demo.entity.TicketEntity;
import com.cinema.demo.entity.UserEntity;
import com.cinema.demo.repository.InvoiceRepository;
import com.cinema.demo.repository.NotificationRepository;
import com.cinema.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private NotificationRepository notificationRepository;


    public TicketConfirmationDTO generateConfirmation(int ticketId) {
        TicketEntity ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket ID"));

        InvoiceEntity invoice = ticket.getInvoice();

        return new TicketConfirmationDTO(
                invoice.getUser().getFullName(),
                invoice.getUser().getEmail(),
                ticket.getSeatNumber(),
                ticket.getStartTime(),
                ticket.getEndTime(),
                invoice.getStatus(),
                ticket.getStatus(),
                invoice.getTotalAmount().toString()
        );
    }

    public List<NotificationEntity> getUnreadNotifications(UserEntity user) {
        return notificationRepository.findByUserAndIsReadFalse(user);
    }
}
