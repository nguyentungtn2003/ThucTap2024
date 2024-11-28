package com.cinema.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketConfirmationDTO {
    private String userFullName;
    private String email;
    private String seatNumber;
    private String showTimeStart;
    private String showTimeEnd;
    private String paymentStatus;
    private String ticketStatus;
    private String totalAmount;
}
