package com.cinema.demo.repository.impl;

import com.cinema.demo.dto.InvoiceTicketDTO;
import com.cinema.demo.dto.TicketInfoDTO;
import com.cinema.demo.entity.*;
import com.cinema.demo.repository.InvoiceRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
@Repository
public class InvoiceRepositoryImpl implements InvoiceRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<InvoiceTicketDTO> getInvoiceTicketsWithCriteria() {
        String sql = """
            SELECT\s
                             i.id AS invoice_id,
                             MAX(m.movie_name) AS movieName,
                             MAX(u.email) AS email,
                             GROUP_CONCAT(DISTINCT st.seat_position) AS seatName,
                             COUNT(t.ticket_id) AS ticketCount,
                             GROUP_CONCAT(t.seat_number) AS seatNumbers,
                             SUM(i.total_amount) AS totalAmount,
                             i.status AS status
                         FROM\s
                             invoice i
                         JOIN\s
                             ticket t ON i.id = t.invoice_id
                         JOIN\s
                             showtime s ON t.showtime_id = s.showtime_id
                         JOIN\s
                             movie m ON s.movie_id = m.movie_id
                         JOIN\s
                             user u ON i.user_id = u.id
                         JOIN\s
                             seat st ON st.showtime_id = s.showtime_id

                         GROUP BY\s
                             i.id
                         LIMIT 0, 1000;

        """;

        ////            WHERE
        ////                i.status = 'SUCCESS'
        //cái này là ở dưới dòng 46 comment tạm
        List<Object[]> results = entityManager.createNativeQuery(sql).getResultList();

        // Mapping kết quả vào DTO
        List<InvoiceTicketDTO> invoiceTicketDTOs = results.stream().map(result -> {
            Integer invoiceId   = (Integer) result[0];
            String movieName = (String) result[1];
            String email = (String) result[2];
            String seatName = (String) result[3];
            int ticketCount = ((Number) result[4]).intValue();

            // Chuyển seatNumbers từ chuỗi thành List<TicketInfoDTO>
            String seatNumbersStr = (String) result[5];
            List<TicketInfoDTO> seatNumbers = Arrays.stream(seatNumbersStr.split(","))
                    .map(seat -> new TicketInfoDTO(seatName, ticketCount, seat))
                    .toList();

            // Chuyển totalAmount thành BigDecimal
            BigDecimal totalAmount = new BigDecimal(((Number) result[6]).doubleValue());
            String status = (String) result[7];

            return new InvoiceTicketDTO(
                    invoiceId.intValue(),
                    movieName,
                    email,
                    seatNumbers,
                    totalAmount,
                    status
            );
        }).toList();

        return invoiceTicketDTOs;
    }
}


