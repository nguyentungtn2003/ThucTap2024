package com.cinema.demo.repository;

import com.cinema.demo.dto.InvoiceTicketDTO;

import java.util.List;

public interface InvoiceRepositoryCustom {
    List<InvoiceTicketDTO> getInvoiceTicketsWithCriteria();

}
