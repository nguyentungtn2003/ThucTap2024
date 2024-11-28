package com.cinema.demo.service;

import com.cinema.demo.dto.InvoiceDetailsDTO;
import com.cinema.demo.dto.InvoiceTicketDTO;
import com.cinema.demo.entity.InvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public interface InvoiceService {

    /**
     * Lấy danh sách hóa đơn và thông tin vé kèm theo (phân trang)
     * @param pageable thông tin phân trang
     * @return danh sách hóa đơn theo phân trang
     */
    Page<InvoiceTicketDTO> getInvoiceTickets(Pageable pageable);

    /**
     * Xóa vé liên quan đến một hóa đơn theo invoiceId
     * @param invoiceId ID của hóa đơn
     * @return true nếu xóa thành công, false nếu thất bại
     */
    boolean deleteTicketByInvoiceId(int invoiceId);

    /**
     * Lấy danh sách hóa đơn theo userId
     * @param userId ID của người dùng
     * @return danh sách hóa đơn của người dùng
     */
    List<InvoiceEntity> getInvoicesByUserId(int userId);

    /**
     * Lấy chi tiết hóa đơn theo ID
     * @param id ID của hóa đơn
     * @return thông tin chi tiết hóa đơn
     */
    InvoiceEntity getInvoiceById(int id);

    /**
     * Lấy toàn bộ danh sách hóa đơn trong hệ thống (chỉ dùng cho admin)
     * @return danh sách toàn bộ hóa đơn
     */
    List<InvoiceEntity> getAllInvoices();

    /**
     * Lấy userId dựa vào email (dùng khi cần xác định user từ thông tin đăng nhập)
     * @param email Email của người dùng
     * @return userId tương ứng
     */
    int getUserIdByEmail(String email);
    Optional<InvoiceDetailsDTO> getInvoiceDetailsById(int invoiceId);
}
