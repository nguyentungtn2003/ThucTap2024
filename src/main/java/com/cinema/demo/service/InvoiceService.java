package com.cinema.demo.service;

import com.cinema.demo.dto.InvoiceTicketDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface InvoiceService {
    Page<InvoiceTicketDTO> getInvoiceTickets(Pageable pageable);
    boolean deleteTicketByInvoiceId(int invoiceId);
}
