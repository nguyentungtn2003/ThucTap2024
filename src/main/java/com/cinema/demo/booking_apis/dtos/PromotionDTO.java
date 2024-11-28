package com.cinema.demo.booking_apis.dtos;

import com.cinema.demo.entity.InvoiceEntity;
import lombok.Data;

import java.util.Date;

@Data
public class PromotionDTO {
    private int id;

    private String name;

    private double discountPercentage;

    private Date startDate;

    private Date endDate;

    private InvoiceDTO invoiceEntity;
}
