package com.cinema.demo.booking_apis.repository;

import com.cinema.demo.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITicketRepository extends JpaRepository<TicketEntity, Integer> {
    List<TicketEntity> findTicketEntityByShowtime_ShowtimeId(Integer showtimeId);
//    // Tìm vé theo showtimeId và seatId
//    @Query("SELECT t FROM TicketEntity t " +
//            "WHERE t.showtime.showtimeId = :showtimeId " +
//            "AND t.seatNumber IN (" +
//            "   SELECT s.seatPosition FROM SeatEntity s " +
//            "   WHERE s.seatId = :seatId)")
//    List<TicketEntity> findTicketsByShowtimeIdAndSeatId(@Param("showtimeId") Integer showtimeId,
//                                                        @Param("seatId") Integer seatId);
    List<TicketEntity> findTicketEntityByShowtime_ShowtimeIdAndSeat_SeatId(Integer showtimeId,Integer seatId);
    @Query("SELECT t FROM TicketEntity t WHERE t.invoiceEntity.id IN (SELECT b.id FROM InvoiceEntity b WHERE b.user.id=:userId) ORDER BY t.ticketId DESC")
    List<TicketEntity> findTicketsByUserId(@Param("userId") Long userId);

}