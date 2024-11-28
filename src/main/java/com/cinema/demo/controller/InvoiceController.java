package com.cinema.demo.controller;

import com.cinema.demo.dto.InvoiceDetailsDTO;
import com.cinema.demo.entity.InvoiceEntity;
import com.cinema.demo.service.InvoiceService;
import com.cinema.demo.service.NotificationService;
import com.cinema.demo.service.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private NotificationService notificationService;

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
    public String handleVNPayReturn(@RequestParam Map<String, String> allParams, Principal principal, Model model) {
        try {
            logger.info("Handling VNPay return URL...");
            String result = vnPayService.processReturnUrl(allParams);

            // Kiểm tra kết quả thanh toán
            if ("success".equalsIgnoreCase(result)) {
                // Lấy email người dùng từ Principal
                String email = principal.getName();
                Long userId = (long) invoiceService.getUserIdByEmail(email);

                // Lấy thông tin hóa đơn từ tham số (giả sử invoiceId được trả về từ VNPay)
                int invoiceId = Integer.parseInt(allParams.get("invoiceId"));

                // Tạo thông báo mới
                String message = "Hóa đơn #" + invoiceId + " đã được thanh toán thành công!";
                String link = "/invoices/" + invoiceId + "/details"; // Liên kết đến chi tiết hóa đơn
                notificationService.createNotification(userId, message, link);

                // Chuyển hướng về danh sách hóa đơn
                return "redirect:/invoices";
            } else {
                model.addAttribute("error", "Thanh toán không thành công.");
                return "error-page";
            }
        } catch (Exception e) {
            logger.severe("Error while handling VNPay return: " + e.getMessage());
            model.addAttribute("error", "An error occurred while processing the payment result.");
            return "error-page";
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
