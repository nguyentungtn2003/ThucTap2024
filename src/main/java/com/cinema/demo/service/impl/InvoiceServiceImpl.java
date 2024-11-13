package com.cinema.demo.service.impl;

import com.cinema.demo.dto.InvoiceTicketDTO;
import com.cinema.demo.entity.TicketEntity;
import com.cinema.demo.repository.InvoiceRepository;
import com.cinema.demo.repository.TicketRepository;
import com.cinema.demo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final TicketRepository ticketRepository;
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceServiceImpl(TicketRepository ticketRepository, InvoiceRepository invoiceRepository) {
        this.ticketRepository = ticketRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Page<InvoiceTicketDTO> getInvoiceTickets(Pageable pageable) {
        return invoiceRepository.getInvoiceTicketsWithCriteria(pageable); // Trả về kết quả phân trang từ repository
    }


    @Override
    public boolean deleteTicketByInvoiceId(int invoiceId) {
        try {
            // Lấy tất cả vé liên quan đến invoiceId
            List<TicketEntity> tickets = ticketRepository.findByInvoiceId(invoiceId);

            // Kiểm tra nếu có vé liên quan
            if (tickets != null && !tickets.isEmpty()) {
                // Xóa tất cả vé liên quan
                ticketRepository.deleteAll(tickets);
            }

            // Xóa hóa đơn
            invoiceRepository.deleteById(invoiceId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}



