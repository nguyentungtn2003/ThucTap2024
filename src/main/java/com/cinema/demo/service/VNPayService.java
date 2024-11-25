package com.cinema.demo.service;

import com.cinema.demo.entity.InvoiceEntity;
import com.cinema.demo.repository.InvoiceRepository;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

@Service
public class VNPayService {

    private static final Logger logger = Logger.getLogger(VNPayService.class.getName());

    @Autowired
    private InvoiceRepository invoiceRepository;

    private final String vnp_TmnCode = "Z8VU3X39"; // TmnCode từ VNPAY
    private final String vnp_HashSecret = "ID8F9BSBW9NSR0TV1N8Y45PMVFMKWVHA"; // Secret key
    private final String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private final String vnp_ReturnUrl = "http://localhost:8080/invoices/vnpay-return";

    // Tạo URL thanh toán cho hóa đơn
    public String createPaymentUrl(int invoiceId) {
        try {
            logger.info("Starting to create payment URL for Invoice ID: " + invoiceId);

            // Lấy hóa đơn từ DB
            InvoiceEntity invoice = invoiceRepository.findById(invoiceId)
                    .orElseThrow(() -> new IllegalArgumentException("Invoice not found with ID: " + invoiceId));

            // Kiểm tra trạng thái hóa đơn
            if (!"PENDING".equalsIgnoreCase(invoice.getStatus())) {
                throw new IllegalStateException("Invoice is not in a payable state. Current status: " + invoice.getStatus());
            }

            if (invoice.getTotalAmount() == null || invoice.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Invalid total amount for invoice: " + invoice.getTotalAmount());
            }

            // Chuẩn bị tham số
            Map<String, String> params = new TreeMap<>();
            params.put("vnp_Version", "2.1.0");
            params.put("vnp_Command", "pay");
            params.put("vnp_TmnCode", vnp_TmnCode);
            params.put("vnp_Amount", String.valueOf(invoice.getTotalAmount().multiply(new BigDecimal(100)).intValue()));
            params.put("vnp_CurrCode", "VND");
            params.put("vnp_TxnRef", String.valueOf(invoice.getId()));  // Sử dụng invoiceId làm TxnRef
            params.put("vnp_OrderInfo", "Payment for Invoice ID: " + invoice.getId());
            params.put("vnp_OrderType", "other");
            params.put("vnp_Locale", "vn");
            params.put("vnp_ReturnUrl", vnp_ReturnUrl);
            params.put("vnp_CreateDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
            params.put("vnp_IpAddr", "127.0.0.1");
            params.put("vnp_BankCode", "NCB");

            // Xây dựng hashData và queryData
            StringBuilder hashData = new StringBuilder();
            StringBuilder queryData = new StringBuilder();
            params.forEach((key, value) -> {
                try {
                    hashData.append(key).append("=").append(URLEncoder.encode(value, StandardCharsets.UTF_8.toString())).append("&");
                    queryData.append(key).append("=").append(URLEncoder.encode(value, StandardCharsets.UTF_8.toString())).append("&");
                } catch (Exception e) {
                    throw new RuntimeException("Error encoding parameter: " + key + " with value: " + value, e);
                }
            });

            // Kiểm tra rỗng trước khi loại bỏ ký tự cuối cùng
            if (hashData.length() > 0) {
                hashData.setLength(hashData.length() - 1); // Loại bỏ ký tự "&"
            }
            if (queryData.length() > 0) {
                queryData.setLength(queryData.length() - 1); // Loại bỏ ký tự "&"
            }

            // Tạo SecureHash
            String secureHash = HmacSHA512(vnp_HashSecret, hashData.toString());
            logger.info("Generated SecureHash: " + secureHash);

            // Tạo URL thanh toán
            String paymentUrl = vnp_Url + "?" + queryData + "&vnp_SecureHash=" + secureHash;
            logger.info("Payment URL: " + paymentUrl);

            return paymentUrl;

        } catch (Exception e) {
            logger.severe("Error creating payment URL: " + e.getMessage());
            throw new RuntimeException("Error creating payment URL", e);
        }
    }

    // Xử lý kết quả trả về từ VNPay
    public String processReturnUrl(Map<String, String> params) {
        try {
            logger.info("Processing VNPay return URL...");

            // Lấy và loại bỏ chữ ký (SecureHash) từ các tham số trả về
            String secureHash = params.remove("vnp_SecureHash");

            // Sắp xếp tham số theo key
            StringBuilder hashData = new StringBuilder();
            params.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey()) // Sắp xếp tham số theo key (alphabetically)
                    .forEach(entry -> {
                        try {
                            hashData.append(entry.getKey())
                                    .append("=")
                                    .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()))  // Đảm bảo mã hóa URL đúng
                                    .append("&");
                        } catch (Exception e) {
                            throw new RuntimeException("Error encoding parameter: " + entry.getKey(), e);
                        }
                    });

            // Loại bỏ ký tự "&" cuối cùng
            if (hashData.length() > 0) {
                hashData.setLength(hashData.length() - 1);
            }

            // Tính toán chữ ký mới từ các tham số đã sắp xếp
            String calculatedHash = HmacSHA512(vnp_HashSecret, hashData.toString());

            // Kiểm tra chữ ký có hợp lệ không
            if (!calculatedHash.equals(secureHash)) {
                logger.severe("Invalid signature detected.");
                throw new SecurityException("Invalid signature");
            }

            // Kiểm tra mã phản hồi từ VNPay
            String responseCode = params.get("vnp_ResponseCode");
            String invoiceId = params.get("vnp_TxnRef"); // Lấy invoiceId từ VNPay
            logger.info("Transaction Reference (Invoice ID): " + invoiceId);
            logger.info("Response Code: " + responseCode);

            // Tìm hóa đơn từ database
            InvoiceEntity invoice = invoiceRepository.findById(Integer.parseInt(invoiceId))
                    .orElseThrow(() -> new IllegalArgumentException("Invoice not found with ID: " + invoiceId));

            // Cập nhật trạng thái hóa đơn nếu thanh toán thành công
            if ("00".equals(responseCode)) {
                invoice.setStatus("COMPLETED");  // Chuyển trạng thái thành COMPLETED
                logger.info("Invoice " + invoiceId + " updated to COMPLETED.");
            } else {
                invoice.setStatus("Pending");  // Nếu không thành công, đánh dấu FAILED
                logger.warning("Invoice " + invoiceId + " updated to FAILED.");
            }

            // Lưu lại trạng thái mới của hóa đơn
            invoiceRepository.save(invoice);

            // Lấy danh sách hóa đơn sau khi cập nhật và chuyển hướng về trang danh sách hóa đơn
            return "00".equals(responseCode) ? "redirect:/invoices" : "redirect:/error-page";

        } catch (Exception e) {
            logger.severe("Error processing return URL: " + e.getMessage());
            throw new RuntimeException("Error processing return URL", e);
        }
    }

    // Phương thức để tạo HMAC SHA512
    private String HmacSHA512(String key, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512"));
            return Hex.encodeHexString(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("Error generating HMACSHA512 hash", e);
        }
    }
}
