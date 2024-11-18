package com.cinema.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class InvoiceDetailsDTO {
    private int invoiceId;
    private BigDecimal totalAmount;
    private String invoiceStatus;
    private LocalDateTime invoiceCreatedAt;
    private String movieName;
    private Date movieReleaseDate;
    private String showDateStart;
    private String showDateEnd;
    private String concessionType;
    private int concessionPrice;
    private int concessionQuantity;
    private BigDecimal concessionOrderPrice;
    private BigDecimal ticketPrice;

    // Constructor
    public InvoiceDetailsDTO(int invoiceId, BigDecimal totalAmount, String invoiceStatus, LocalDateTime invoiceCreatedAt,
                             String movieName, Date movieReleaseDate, String showDateStart, String showDateEnd,
                             String concessionType, int concessionPrice, int concessionQuantity, BigDecimal concessionOrderPrice,
                             BigDecimal ticketPrice) {
        this.invoiceId = invoiceId;
        this.totalAmount = totalAmount;
        this.invoiceStatus = invoiceStatus;
        this.invoiceCreatedAt = invoiceCreatedAt;
        this.movieName = movieName;
        this.movieReleaseDate = movieReleaseDate;
        this.showDateStart = showDateStart;
        this.showDateEnd = showDateEnd;
        this.concessionType = concessionType;
        this.concessionPrice = concessionPrice;
        this.concessionQuantity = concessionQuantity;
        this.concessionOrderPrice = concessionOrderPrice;
        this.ticketPrice = ticketPrice;
    }

    // Getters and Setters
    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public LocalDateTime getInvoiceCreatedAt() {
        return invoiceCreatedAt;
    }

    public void setInvoiceCreatedAt(LocalDateTime invoiceCreatedAt) {
        this.invoiceCreatedAt = invoiceCreatedAt;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(Date movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getShowDateStart() {
        return showDateStart;
    }

    public void setShowDateStart(String showDateStart) {
        this.showDateStart = showDateStart;
    }

    public String getShowDateEnd() {
        return showDateEnd;
    }

    public void setShowDateEnd(String showDateEnd) {
        this.showDateEnd = showDateEnd;
    }

    public String getConcessionType() {
        return concessionType;
    }

    public void setConcessionType(String concessionType) {
        this.concessionType = concessionType;
    }

    public int getConcessionPrice() {
        return concessionPrice;
    }

    public void setConcessionPrice(int concessionPrice) {
        this.concessionPrice = concessionPrice;
    }

    public int getConcessionQuantity() {
        return concessionQuantity;
    }

    public void setConcessionQuantity(int concessionQuantity) {
        this.concessionQuantity = concessionQuantity;
    }

    public BigDecimal getConcessionOrderPrice() {
        return concessionOrderPrice;
    }

    public void setConcessionOrderPrice(BigDecimal concessionOrderPrice) {
        this.concessionOrderPrice = concessionOrderPrice;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
