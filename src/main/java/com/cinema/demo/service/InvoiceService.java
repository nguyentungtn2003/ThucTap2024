package com.cinema.demo.service;

import com.cinema.demo.dto.InvoiceTicketDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface InvoiceService {
    List<InvoiceTicketDTO> getInvoiceTickets();

}
