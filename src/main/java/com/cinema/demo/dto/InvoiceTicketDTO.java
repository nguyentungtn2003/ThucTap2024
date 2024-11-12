package com.cinema.demo.dto;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceTicketDTO {

    private int id;
    private String movieName;
    private String email;
    private List<TicketInfoDTO> tickets; // Sử dụng TicketInfoDTO thay vì TicketInfo
    private BigDecimal totalAmount;
    private String status;
    public InvoiceTicketDTO() {
    }
    // Constructor
    public InvoiceTicketDTO(int id, String movieName, String email, List<TicketInfoDTO> tickets, BigDecimal totalAmount, String status) {
        this.id = id;
        this.movieName = movieName;
        this.email = email;
        this.tickets = tickets;  // Sử dụng danh sách TicketInfoDTO
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TicketInfoDTO> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketInfoDTO> tickets) {
        this.tickets = tickets;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
