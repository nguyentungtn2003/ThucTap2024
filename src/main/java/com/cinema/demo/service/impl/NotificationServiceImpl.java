package com.cinema.demo.service.impl;

import com.cinema.demo.dto.TicketConfirmationDTO;
import com.cinema.demo.entity.InvoiceEntity;
import com.cinema.demo.entity.NotificationEntity;
import com.cinema.demo.entity.TicketEntity;
import com.cinema.demo.entity.UserEntity;
import com.cinema.demo.repository.InvoiceRepository;
import com.cinema.demo.repository.NotificationRepository;
import com.cinema.demo.repository.TicketRepository;
import com.cinema.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl  implements NotificationService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private NotificationRepository notificationRepository;


    public TicketConfirmationDTO generateConfirmation(int ticketId) {
        TicketEntity ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket ID"));

        InvoiceEntity invoice = ticket.getInvoiceEntity();

        return new TicketConfirmationDTO(
                invoice.getUser().getName(),
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

    @Override
    public void createNotification(Long userId, String message, String link) {
        NotificationEntity notification = new NotificationEntity();
        notification.setId(userId);
        notification.setMessage(message);
        notification.setLink(link);
        notification.setRead(false); // Thông báo mới chưa đọc
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification); // Lưu thông báo vào database
    }
}
