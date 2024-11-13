package com.cinema.demo.repository;

import com.cinema.demo.dto.InvoiceTicketDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InvoiceRepositoryCustom {
//    List<InvoiceTicketDTO> getInvoiceTicketsWithCriteria();
 Page<InvoiceTicketDTO> getInvoiceTicketsWithCriteria(Pageable pageable);
}
