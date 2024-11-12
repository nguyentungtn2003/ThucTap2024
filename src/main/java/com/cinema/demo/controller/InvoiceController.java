package com.cinema.demo.controller;

import com.cinema.demo.dto.InvoiceTicketDTO;
import com.cinema.demo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/ticket-management")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/tickets")
    public String getInvoiceTickets(Model model) {
        List<InvoiceTicketDTO> invoiceTickets = invoiceService.getInvoiceTickets();
        System.out.println(invoiceTickets);
        model.addAttribute("invoiceTickets", invoiceTickets);  // Thêm dữ liệu vào model
        return "admin/ticket_management";  // Trả về tên file Thymeleaf (ticket_management.html)
    }
}
