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

    private ShowtimeDTO showtime;

    private BigDecimal price;

    private String status;

    private SeatDTO seat;
//    private Integer seatId;

    private InvoiceDTO invoiceEntity;
}
