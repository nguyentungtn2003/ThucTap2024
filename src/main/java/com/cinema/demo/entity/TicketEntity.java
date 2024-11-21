package com.cinema.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Ticket")
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;

    private String qrImageURL;

    @ManyToOne
    @JoinColumn(name = "showtimeId")
    private ShowtimeEntity showtime;

    private BigDecimal price;

    private String status;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false,name = "seat_id")
    private SeatEntity seat;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="invoice_id")
    private InvoiceEntity invoiceEntity;

}
