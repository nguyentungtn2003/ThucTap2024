package com.cinema.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Ticket")
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;

    @ManyToOne
    @JoinColumn(name = "showtimeId")
    private ShowtimeEntity showtime;

    private BigDecimal price;

    private String seatNumber;

    private String status;

    private String startTime;
    private String endTime;

    @ManyToOne
    @JoinColumn(name = "invoiceId")  // Thêm mối quan hệ với InvoiceEntity
    private InvoiceEntity invoice;
}
