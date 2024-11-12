package com.cinema.demo.service.impl;

import com.cinema.demo.dto.InvoiceTicketDTO;
import com.cinema.demo.repository.InvoiceRepository;
import com.cinema.demo.repository.InvoiceRepositoryCustom;
import com.cinema.demo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<InvoiceTicketDTO> getInvoiceTickets() {
        return invoiceRepository.getInvoiceTicketsWithCriteria(); // Using the custom method here
    }



}
