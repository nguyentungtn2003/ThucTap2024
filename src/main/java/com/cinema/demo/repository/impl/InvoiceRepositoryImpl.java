package com.cinema.demo.repository.impl;

import com.cinema.demo.dto.InvoiceTicketDTO;
import com.cinema.demo.dto.TicketInfoDTO;
import com.cinema.demo.repository.InvoiceRepositoryCustom;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Repository
public class InvoiceRepositoryImpl implements InvoiceRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<InvoiceTicketDTO> getInvoiceTicketsWithCriteria(Pageable pageable) {
        String sql = """
        SELECT
            i.id AS invoice_id,
            MAX(m.movie_name) AS movieName,
            MAX(u.email) AS email,
            GROUP_CONCAT(DISTINCT st.seat_position) AS seatName,
            COUNT(t.ticket_id) AS ticketCount,
            GROUP_CONCAT(t.seat_number) AS seatNumbers,
            SUM(i.total_amount) AS totalAmount,
            i.status AS status
        FROM
            invoice i
        JOIN
            ticket t ON i.id = t.invoice_id
        JOIN
            showtime s ON t.showtime_id = s.showtime_id
        JOIN
            movie m ON s.movie_id = m.movie_id
        JOIN
            user u ON i.user_id = u.user_id
        JOIN
            seat st ON st.showtime_id = s.showtime_id
        GROUP BY
            i.id
        ORDER BY
            i.id
        LIMIT :pageSize OFFSET :offset;
    """;

        String countSql = """
        SELECT COUNT(DISTINCT i.id)
        FROM
            invoice i
        JOIN
            ticket t ON i.id = t.invoice_id
        JOIN
            showtime s ON t.showtime_id = s.showtime_id
        JOIN
            movie m ON s.movie_id = m.movie_id
        JOIN
            user u ON i.user_id = u.user_id
        JOIN
            seat st ON st.showtime_id = s.showtime_id;
    """;

        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int pageSize = pageable.getPageSize();

        // Lấy danh sách invoice
        List<Object[]> results = entityManager.createNativeQuery(sql)
                .setParameter("pageSize", pageSize)
                .setParameter("offset", offset)
                .getResultList();

        // Tính tổng số lượng bản ghi
        Long totalElements = ((Number) entityManager.createNativeQuery(countSql).getSingleResult()).longValue();

        // Mapping kết quả trả về danh sách DTO
        List<InvoiceTicketDTO> invoiceTicketDTOs = results.stream().map(result -> {
            Integer invoiceId = (Integer) result[0];
            String movieName = (String) result[1];
            String email = (String) result[2];
            String seatName = (String) result[3];
            int ticketCount = ((Number) result[4]).intValue();
            String seatNumbersStr = (String) result[5];
            List<TicketInfoDTO> seatNumbers = Arrays.stream(seatNumbersStr.split(","))
                    .map(seat -> new TicketInfoDTO(seatName, ticketCount, seat))
                    .toList();
            BigDecimal totalAmount = new BigDecimal(((Number) result[6]).doubleValue());
            String status = (String) result[7];

            return new InvoiceTicketDTO(
                    invoiceId,
                    movieName,
                    email,
                    seatNumbers,
                    totalAmount,
                    status
            );
        }).toList();

        // Trả về đối tượng Page với tổng số lượng bản ghi chính xác
        return new PageImpl<>(invoiceTicketDTOs, pageable, totalElements);
    }

}



