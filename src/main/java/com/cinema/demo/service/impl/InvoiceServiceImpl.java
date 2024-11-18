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
    public List<InvoiceDetailsDTO> getInvoiceDetailsById(int invoiceId) {
        // Tạo CriteriaBuilder và CriteriaQuery
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<InvoiceDetailsDTO> query = cb.createQuery(InvoiceDetailsDTO.class);
        Root<InvoiceEntity> invoice = query.from(InvoiceEntity.class);

        // Join các bảng cần thiết
        Join<InvoiceEntity, TicketEntity> ticket = invoice.join("tickets", JoinType.INNER);
        Join<TicketEntity, MovieEntity> movie = ticket.join("showtime", JoinType.INNER).join("movie", JoinType.INNER);
        Join<InvoiceEntity, ConcessionOrderEntity> concessionOrder = invoice.join("concessionOrders", JoinType.LEFT);
        Join<ConcessionOrderEntity, TypeOfConcessionEntity> concessionType = concessionOrder.join("concessionType", JoinType.LEFT);

        // Chọn các trường cần thiết để trả về InvoiceDetailsDTO
        query.select(cb.construct(
                InvoiceDetailsDTO.class,
                invoice.get("id"),
                invoice.get("totalAmount"),
                invoice.get("status"),
                invoice.get("createdAt"),
                movie.get("movieName"),
                movie.get("releaseDate"),
                ticket.get("startTime"),
                ticket.get("endTime"),
                concessionType.get("productType"),
                concessionType.get("price"),
                concessionOrder.get("quantity"),
                concessionOrder.get("price"),
                ticket.get("price")
        ));

        // Điều kiện WHERE để chỉ lấy dữ liệu của invoiceId
        query.where(cb.equal(invoice.get("id"), invoiceId));

        // Thực hiện truy vấn và trả về kết quả
        return entityManager.createQuery(query).getResultList();
    }
}
