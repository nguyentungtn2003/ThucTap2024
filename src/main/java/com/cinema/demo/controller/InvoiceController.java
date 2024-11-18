package com.cinema.demo.controller;

import com.cinema.demo.dto.InvoiceDetailsDTO;
import com.cinema.demo.entity.InvoiceEntity;
import com.cinema.demo.service.InvoiceService;
import com.cinema.demo.service.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class InvoiceController {

    private static final Logger logger = Logger.getLogger(InvoiceController.class.getName());

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private VNPayService vnPayService;

    @GetMapping("/invoices")
    public String listInvoices(Model model, Principal principal) {
        try {
            String email = principal.getName();
            int userId = invoiceService.getUserIdByEmail(email);

            List<InvoiceEntity> invoices = invoiceService.getInvoicesByUserId(userId);
            model.addAttribute("invoices", invoices);

            return "invoices";
        } catch (Exception e) {
            logger.severe("Error fetching invoices: " + e.getMessage());
            model.addAttribute("error", "Unable to fetch invoices.");
            return "error-page";
        }
    }


    @GetMapping("/invoices/{id}/pay")
    public String payInvoice(@PathVariable int id) {
        try {
            String paymentUrl = vnPayService.createPaymentUrl(id);
            logger.info("Redirecting to VNPay payment URL: " + paymentUrl);
            return "redirect:" + paymentUrl;
        } catch (Exception e) {
            logger.severe("Error creating payment URL: " + e.getMessage());
            return "redirect:/invoices?error=Unable to initiate payment";
        }
    }

    @GetMapping("/invoices/vnpay-return")
    public String handleVNPayReturn(@RequestParam Map<String, String> allParams, Model model) {
        try {
            logger.info("Handling VNPay return URL...");
            String result = vnPayService.processReturnUrl(allParams);

            // Sau khi xử lý xong, chuyển hướng về trang danh sách hóa đơn
            return "redirect:/invoices";  // Đây là cách sử dụng redirect trong Spring MVC để chuyển hướng đến trang "/invoices"

        } catch (Exception e) {
            logger.severe("Error while handling VNPay return: " + e.getMessage());
            model.addAttribute("error", "An error occurred while processing the payment result.");
            return "error-page";  // Nếu có lỗi, hiển thị trang lỗi
        }
    }

    // Hiển thị chi tiết hóa đơn
    @GetMapping("/invoices/{id}/details")
    public String invoiceDetails(@PathVariable int id, Model model) {
        try {
            // Gọi service để lấy chi tiết hóa đơn
            List<InvoiceDetailsDTO> invoiceDetails = invoiceService.getInvoiceDetailsById(id);
            if (invoiceDetails.isEmpty()) {
                model.addAttribute("error", "No invoice found with the given ID.");
                return "error-page";
            }

            model.addAttribute("invoiceDetails", invoiceDetails.get(0)); // Hiển thị thông tin hóa đơn chi tiết
            return "invoice-details"; // Trả về trang chi tiết hóa đơn
        } catch (Exception e) {
            logger.severe("Error fetching invoice details: " + e.getMessage());
            model.addAttribute("error", "Unable to fetch invoice details.");
            return "error-page";
        }
    }

}
