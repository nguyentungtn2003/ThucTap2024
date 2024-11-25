package com.cinema.demo.booking_apis.dtos;

import com.cinema.demo.entity.InvoiceEntity;
import com.cinema.demo.entity.SeatEntity;
import com.cinema.demo.entity.ShowtimeEntity;
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
