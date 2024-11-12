package com.cinema.demo.dto;

public class TicketInfoDTO {

    private String seatType;
    private long quantity;
    private String seatNumbers;

    // Constructor
    public TicketInfoDTO(String seatType, long quantity, String seatNumbers) {
        this.seatType = seatType;
        this.quantity = quantity;
        this.seatNumbers = seatNumbers;
    }

    // Getter và Setter
    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(String seatNumbers) {
        this.seatNumbers = seatNumbers;
    }
}
