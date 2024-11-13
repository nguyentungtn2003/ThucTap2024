package com.cinema.demo.controller;

import com.cinema.demo.dto.InvoiceTicketDTO;
import com.cinema.demo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class TicketController {

    private final InvoiceService invoiceService;

    @Autowired
    public TicketController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/tickets")
    public String getInvoiceTickets(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "2") int size,
                                    Model model) {
        // Điều chỉnh giá trị page để tránh lỗi ngoài giới hạn
        page = Math.max(0, page);  // Đảm bảo page không nhỏ hơn 0

        Pageable pageable = PageRequest.of(page, size);
        Page<InvoiceTicketDTO> invoiceTickets = invoiceService.getInvoiceTickets(pageable);

        // Điều chỉnh page nếu vượt quá số lượng trang
        if (page >= invoiceTickets.getTotalPages() && invoiceTickets.getTotalPages() > 0) {
            page = invoiceTickets.getTotalPages() - 1;
            pageable = PageRequest.of(page, size);
            invoiceTickets = invoiceService.getInvoiceTickets(pageable);
        }
        System.out.println("currentPage: " + page + ", pageSize: " + size + ", totalPages: " + invoiceTickets.getTotalPages());


        model.addAttribute("invoiceTickets", invoiceTickets);
        model.addAttribute("pageSize", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", invoiceTickets.getTotalPages());
        return "admin/ticket_management";  // Tên template Thymeleaf
    }

    @PostMapping("/tickets/delete/{invoiceId}")
    public String deleteTicket(@PathVariable int invoiceId, Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "2") int size) {
        boolean isDeleted = invoiceService.deleteTicketByInvoiceId(invoiceId);
        String message = isDeleted ? "Ticket deleted successfully" : "Ticket deletion failed";
        model.addAttribute("message", message);

        // Điều hướng lại trang quản lý vé với thông tin phân trang
        return "redirect:/admin/tickets?page=" + page + "&size=" + size;
    }
}
