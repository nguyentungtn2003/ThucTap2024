package com.cinema.demo.booking_apis.dtos;

import com.cinema.demo.entities.InvoiceEntity;
import com.cinema.demo.entities.SeatEntity;
import com.cinema.demo.entities.ShowtimeEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketDTO {
    private int ticketId;

    private String qrImageURL;

    private ShowtimeEntity showtime;

    private BigDecimal price;

    private String status;

    private SeatEntity seat;

    private InvoiceEntity invoiceEntity;
}
