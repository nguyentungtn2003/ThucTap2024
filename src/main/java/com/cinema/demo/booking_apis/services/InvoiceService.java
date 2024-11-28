package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.dtos.BookingRequestDTO;
import com.cinema.demo.booking_apis.repository.IInvoiceRepository;
import com.cinema.demo.booking_apis.repository.ISeatRepository;
import com.cinema.demo.booking_apis.repository.IShowTimeRepository;
import com.cinema.demo.booking_apis.repository.ITicketRepository;
import com.cinema.demo.entity.*;
import com.cinema.demo.security.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class InvoiceService implements IInvoiceService {
    @Autowired
    private IShowTimeRepository showTimeRepository;
    @Autowired
    private ITicketRepository ticketRepository;
    @Autowired
    private IUserRepository IUserRepository;
    @Autowired
    private ISeatRepository seatRepository;
    @Autowired
    private IInvoiceRepository invoiceRepository;

    @Override
    @Transactional
    public void createNewInvoice(BookingRequestDTO bookingRequestDTO) throws RuntimeException {

        //Lấy ra lịch
        ShowtimeEntity showtime = showTimeRepository.getById(bookingRequestDTO.getShowtimeId());
        //Lấy ra người dùng
        UserEntity user = IUserRepository.getById(bookingRequestDTO.getUserId());

        //Lưu Bill gồm thông tin người dùng xuống trước
        InvoiceEntity invoiceToCreate = new InvoiceEntity();
        invoiceToCreate.setUser(user);
        invoiceToCreate.setCreatedTime(LocalDateTime.now());
        invoiceToCreate.setStatus("PENDING");
        InvoiceEntity createdInvoice = invoiceRepository.save(invoiceToCreate);

        //Với mỗi ghế ngồi check xem đã có ai đặt chưa, nếu rồi thì throw, roll back luôn còn ko
        //thì đóng gói các thông tin ghế và lịch vào vé và lưu xuống db
        bookingRequestDTO.getSeatIds().forEach(seatId->{
            if(!ticketRepository.findTicketEntityByShowtime_ShowtimeIdAndSeat_SeatId(showtime.getShowtimeId(),seatId)
                    .isEmpty()){// Nếu đã có người đặt vé ghế đó ở lịch cụ thể đó thì
                throw new RuntimeException("Đã có người nhanh tay hơn đặt ghế, mời bạn chọn lại!");
            }
            // Lấy thông tin ghế
            SeatEntity seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new RuntimeException("Ghế không tồn tại: " + seatId));
            // đóng gói lịch, seat và bill vào từng vé rồi add vào list vé
            TicketEntity ticket = new TicketEntity();
            ticket.setShowtime(showtime);
            ticket.setSeat(seatRepository.getById(seatId));
            ticket.setStatus("BOOKED");
            ticket.setInvoiceEntity(createdInvoice);
            ticket.setQrImageURL("https://scontent-sin6-2.xx.fbcdn.net/v/t1.15752-9/268794058_655331555823095_3657556108194277679_n.png?_nc_cat=105&ccb=1-5&_nc_sid=ae9488&_nc_ohc=BrNXGO8HufkAX_OGjWc&_nc_ht=scontent-sin6-2.xx&oh=03_AVK_zaJj7pziY9nLrVqoIQJAzbomu4KPgED1PxFFpYfCrQ&oe=61F778D8");
            ticket.setPrice(new BigDecimal("100.00"));
            TicketEntity createdTicket = ticketRepository.save(ticket);

            // Cập nhật invoice
            createdInvoice.setTotalTicketAmount(createdInvoice.getTotalTicketAmount() + 1);
            createdInvoice.setTotalAmount(createdInvoice.getTotalAmount().add(createdTicket.getPrice()));
        });
        // Cập nhật trạng thái hóa đơn
        createdInvoice.setStatus("COMPLETED");
        invoiceRepository.save(createdInvoice);
    }

    @Override
    // Phương thức lấy hóa đơn theo ID
    public Optional<InvoiceEntity> getInvoiceById(int invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }
}
