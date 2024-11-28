package com.cinema.demo.service.impl;

import com.cinema.demo.dto.InvoiceDetailsDTO;
import com.cinema.demo.dto.InvoiceTicketDTO;
import com.cinema.demo.entity.*;
import com.cinema.demo.repository.InvoiceRepository;
import com.cinema.demo.repository.TicketRepository;
import com.cinema.demo.repository.UserRepository;
import com.cinema.demo.service.InvoiceService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<InvoiceTicketDTO> getInvoiceTickets(Pageable pageable) {
        return invoiceRepository.getInvoiceTicketsWithCriteria(pageable);
    }

    @Override
    public boolean deleteTicketByInvoiceId(int invoiceId) {
        try {
            invoiceRepository.deleteById(invoiceId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<InvoiceEntity> getInvoicesByUserId(int userId) {
        return invoiceRepository.findByUserId(userId);
    }

    @Override
    public InvoiceEntity getInvoiceById(int id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found with ID: " + id));
    }

    @Override
    public List<InvoiceEntity> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public int getUserIdByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        return Math.toIntExact(user.getId());
    }
    @Override
    @Transactional
    public Optional<InvoiceDetailsDTO> getInvoiceDetailsById(int invoiceId) {
        // Tạo CriteriaBuilder và CriteriaQuery
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<InvoiceDetailsDTO> query = cb.createQuery(InvoiceDetailsDTO.class);
        Root<InvoiceEntity> invoice = query.from(InvoiceEntity.class);

        // Join các bảng cần thiết
        Join<InvoiceEntity, TicketEntity> ticket = invoice.join("tickets", JoinType.INNER); // INNER join với bảng TicketEntity
        Join<TicketEntity, MovieEntity> movie = ticket.join("showtime", JoinType.INNER).join("movie", JoinType.INNER); // INNER join với bảng MovieEntity qua ShowTime
        Join<InvoiceEntity, ConcessionOrderEntity> concessionOrder = invoice.join("concessionOrders", JoinType.LEFT); // LEFT join với bảng ConcessionOrderEntity
        Join<ConcessionOrderEntity, TypeOfConcessionEntity> concessionType = concessionOrder.join("concessionType", JoinType.LEFT); // LEFT join với bảng TypeOfConcessionEntity

        // Chọn các trường cần thiết để trả về InvoiceDetailsDTO
        query.select(cb.construct(
                InvoiceDetailsDTO.class,
                invoice.get("id"), // Lấy ID hóa đơn
                invoice.get("totalAmount"), // Lấy tổng số tiền
                invoice.get("status"), // Lấy trạng thái
                invoice.get("createdTime"), // Lấy thời gian tạo
                movie.get("movieName"), // Lấy tên phim
                movie.get("releaseDate"), // Lấy ngày phát hành phim
                ticket.get("startTime"), // Lấy thời gian bắt đầu vé
                ticket.get("endTime"), // Lấy thời gian kết thúc vé
                concessionType.get("productType"), // Lấy loại đồ ăn
                concessionType.get("price"), // Lấy giá đồ ăn
                concessionOrder.get("quantity"), // Lấy số lượng đồ ăn
                concessionOrder.get("price"), // Lấy giá tiền đồ ăn
                ticket.get("price") // Lấy giá vé
        ));

        // Điều kiện WHERE để chỉ lấy dữ liệu của invoiceId
        query.where(cb.equal(invoice.get("id"), invoiceId));

        // Thực hiện truy vấn và trả về kết quả
        List<InvoiceDetailsDTO> result = entityManager.createQuery(query).getResultList();

        // Kiểm tra nếu kết quả trống, trả về Optional.empty, ngược lại trả về Optional với kết quả đầu tiên
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

}
