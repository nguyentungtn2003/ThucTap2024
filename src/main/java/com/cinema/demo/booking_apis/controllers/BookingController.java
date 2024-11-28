package com.cinema.demo.booking_apis.controllers;

import com.cinema.demo.booking_apis.dtos.*;
import com.cinema.demo.booking_apis.services.*;
import com.cinema.demo.entity.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

//@RestController
//@RequestMapping("/api/booking")
//public class BookingController {
//
//    @Autowired
//    private ISeatService seatService;
//
//    @Autowired
//    private IBookingService bookingService; // Thêm dịch vụ booking
//    @Autowired
//    private ISeatBookingService seatBookingService; // Thêm dịch vụ seat booking
//    @Autowired
//    private IShowTimeService showtimeService; // Thêm dịch vụ showtime
//    @Autowired
//    private ITicketService ticketService; // Thêm dịch vụ ticket
//
//    @PostMapping("/book-ticket")
//    public String bookTicket(@RequestParam Integer seatId,
//                             @RequestParam Integer movieId,
//                             @RequestParam Integer showtimeId,
//                             @RequestParam BigDecimal totalAmount,
//                             @RequestParam BigDecimal bookingFee,
//                             HttpServletRequest request,
//                             Model model) {
//
//        // Lấy thông tin ghế từ DB dựa trên seatId
//        SeatDTO seat = seatService.getSeatById(seatId);
//        if (seat == null) {
//            model.addAttribute("error", "Seat not found");
//            return "error";
//        }
//
//        // Tạo BookingDTO
//        BookingDTO booking = new BookingDTO();
//        booking.setTotalFoodTicketAmount(totalAmount);
//        booking.setBookingFee(bookingFee);
//        booking.setStatus("Pending");
//        booking.setCreatedDateTime(LocalDateTime.now());
//
//        // Liên kết Booking với Seat
//        SeatBookingDTO seatBooking = new SeatBookingDTO();
//        seatBooking.setSeat(seat);
//        seatBooking.setBooking(booking);
//
//        // Cập nhật trạng thái ghế là "đã đặt"
//        seat.setIsOccupied(1);  // Đánh dấu ghế đã được đặt
//        seatService.updateSeatStatus(seat);  // Cập nhật trạng thái ghế
//
//        // Lưu booking và seatBooking vào DB (sử dụng service)
//        bookingService.saveBooking(booking); // Lưu booking vào DB
//        seatBookingService.saveSeatBooking(seatBooking); // Lưu SeatBooking vào DB
//
//        // Lấy thông tin về suất chiếu (ShowtimeDTO) từ showtimeId
//        ShowtimeDTO showtime = showtimeService.getShowtimeById(showtimeId);
//
//        // Tạo vé (TicketDTO)
//        TicketDTO ticket = new TicketDTO();
//        ticket.setSeat(seat);
//        ticket.setShowtime(showtime);  // Đặt suất chiếu cho vé
//        ticket.setPrice(totalAmount);  // Đặt giá vé
//        ticket.setStatus("Not Used");  // Trạng thái vé khi mới tạo
//
//        // Lưu vé vào DB
//        ticketService.saveTicket(ticket); // Lưu vé vào DB
//
//        // Trả về thông tin vé cho người dùng
//        model.addAttribute("ticket", ticket); // Thêm vé vào model để hiển thị
//        return "ticket-confirmation";  // Trang hiển thị thông tin vé sau khi đặt
//    }
//}
//
//


@Controller
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private ISeatService seatService;
    @Autowired
    private IBookingService bookingService;
    @Autowired
    private ISeatBookingService seatBookingService;
    @Autowired
    private IShowTimeService showtimeService;
    @Autowired
    private ITicketService ticketService;
    @Autowired
    private IMovieService movieService;
    @Autowired
    private IRoomService iRoomService;

    @Autowired
    private TypeOfConcessionService typeOfConcessionService;

    @Autowired
    private ConcessionOrderService concessionOrderService;

    @Autowired
    private InvoiceService invoiceService;



    @PostMapping("/book-ticket")
    public String bookTicket(@RequestParam List<Integer> seatIds,
                             @RequestParam Integer movieId,
                             @RequestParam Integer showtimeId,
                             @RequestParam BigDecimal bookingFee,
                             HttpServletRequest request,
                             Model model) {

        HttpSession session = request.getSession();
        Integer movie_Id = (Integer) session.getAttribute("movie_Id");
        String start_Date = (String) session.getAttribute("start_Date");
        String start_Time = (String) session.getAttribute("start_Time");
        Integer room_Id = (Integer) session.getAttribute("room_Id");

        List<SeatDTO> selectedSeats = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        // Retrieve movie and showtime
        MovieDTO movie = movieService.getById(movieId);
        ShowtimeDTO showtime = showtimeService.getShowtimeById(showtimeId);
        CinemaRoomDTO cinemaRoom = iRoomService.getById(room_Id);
        if (movie == null || showtime == null) {
            model.addAttribute("error", "Invalid movie or showtime selected.");
            return "boleto/demo/404";
        }

        // Process each selected seat
        for (Integer seatId : seatIds) {
            SeatDTO seat = seatService.getSeatById(seatId);
            if (seat == null) {
                model.addAttribute("error", "Seat not found.");
                return "boleto/demo/404";
            }

            if (seat.getIsOccupied() != 0) {
                model.addAttribute("error", "Seat " + seat.getSeatPosition() + " is no longer available.");
                model.addAttribute("movie_Id", movie_Id);
                model.addAttribute("start_Date", start_Date);
                model.addAttribute("start_Time", start_Time);
                model.addAttribute("room_Id", room_Id);
                return "boleto/demo/404";
            }

            // Update seat status and add to list
            seat.setIsOccupied(1);
            seatService.updateSeatStatus(seat);
            selectedSeats.add(seat);

            // Add seat price to total amount
            BigDecimal seatPrice = seatService.getPriceBySeatType(seat.getSeatType().getSeatName());
            totalAmount = totalAmount.add(seatPrice);
        }

        // Save booking
        BookingDTO booking = new BookingDTO();
        booking.setTotalFoodTicketAmount(totalAmount);
        booking.setBookingFee(bookingFee);
        booking.setStatus("Pending");
        booking.setCreatedDateTime(LocalDateTime.now());
        bookingService.saveBooking(booking);

        // Save seat bookings
        for (SeatDTO seat : selectedSeats) {
            SeatBookingDTO seatBooking = new SeatBookingDTO();
            seatBooking.setBooking(booking);
            seatBooking.setSeat(seat);
            seatBookingService.saveSeatBooking(seatBooking);
        }

        // Save tickets
        List<TicketDTO> tickets = new ArrayList<>();
        for (SeatDTO seat : selectedSeats) {
            TicketDTO ticket = new TicketDTO();
            ticket.setSeat(seat);
            ticket.setShowtime(showtime);
            ticket.setPrice(seatService.getPriceBySeatType(seat.getSeatType().getSeatName()));
            ticket.setStatus("Not Used");
            ticketService.saveTicket(ticket);
//            int ticketId = ticket.getTicketId();
            tickets.add(ticket);
            System.out.println("Ticket ID after save: " + ticket.getTicketId());
        }

        List<TypeOfConcessionEntity> foodList = typeOfConcessionService.getAllConcessions();
        if (foodList.isEmpty()) {
            System.out.println("Food list is empty!");
        } else {
            System.out.println("Food list size: " + foodList.size());
        }
        model.addAttribute("foodList", foodList);

        // Add attributes for the frontend

        model.addAttribute("movie", movie);
        model.addAttribute("showtime", showtime);
        model.addAttribute("selectedSeats", selectedSeats);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("booking", booking);
        model.addAttribute("tickets", tickets);
        model.addAttribute("room_Id", room_Id);
        model.addAttribute("start_Date", start_Date);
        model.addAttribute("start_Time", start_Time);
        model.addAttribute("cinemaRoom", cinemaRoom);
        return "boleto/demo/popcorn";
    }


    @PostMapping("/cancel-booking")
    public String cancelBooking(@RequestParam("ticketId[]") List<Integer> tickets, HttpServletRequest request,Model model) {

        HttpSession session = request.getSession();
        Integer movie_Id = (Integer) session.getAttribute("movie_Id");
        String start_Date = (String) session.getAttribute("start_Date");
        String start_Time = (String) session.getAttribute("start_Time");
        Integer room_Id = (Integer) session.getAttribute("room_Id");
        if (tickets == null || tickets.isEmpty()) {
            model.addAttribute("error", "Ticket IDs are required.");
            return "boleto/demo/404";
        }

        for (Integer ticketId : tickets) {
            TicketDTO ticketDTO = ticketService.getTicketById(ticketId);

            if (ticketDTO == null) {
                model.addAttribute("error", "Booking not found for ticket ID " + ticketId);
                return "boleto/demo/404";
            }

            if ("Canceled".equals(ticketDTO.getStatus())) {
                model.addAttribute("error", "This ticket has already been canceled.");
                return "boleto/demo/404";
            }

                SeatDTO seat = ticketDTO.getSeat();
                seat.setIsOccupied(0);
                seatService.updateSeatStatus(seat);

            ticketDTO.setStatus("Canceled");
            ticketService.saveTicket(ticketDTO);
        }

        model.addAttribute("ticketIds", tickets);
        model.addAttribute("movie_Id", movie_Id);
        model.addAttribute("start_Date", start_Date);
        model.addAttribute("start_Time", start_Time);
        model.addAttribute("room_Id", room_Id);
        model.addAttribute("message", "Your booking(s) have been canceled, and the seats are now available.");
        return "redirect:/seat-selection?movieId=" + movie_Id + "&startDate=" + start_Date + "&startTime=" + start_Time+ "&roomId=" + room_Id;
    }



//    @GetMapping("/food")
//    public String showFoodPage(Model model) {
//
//        List<TypeOfConcessionEntity> foodList = typeOfConcessionService.getAllConcessions();
//        if (foodList.isEmpty()) {
//            System.out.println("Food list is empty!"); // Thêm dòng này để kiểm tra
//        } else {
//            System.out.println("Food list size: " + foodList.size()); // In ra số lượng món ăn
//        }
//        model.addAttribute("foodList", foodList);
//        return "boleto/demo/popcorn";
//    }


    // Xử lý đơn hàng và hiển thị giỏ hàng
    @PostMapping("/order")
    public String orderConcession(@RequestParam("concessionTypeId") int concessionTypeId,
                                  @RequestParam("quantity") int quantity,
                                  @RequestParam("invoiceId") int invoiceId,
                                  Model model) {
        // Lấy InvoiceEntity từ InvoiceService
        Optional<InvoiceEntity> invoiceEntityOpt = invoiceService.getInvoiceById(invoiceId);
        if (invoiceEntityOpt.isEmpty()) {
            model.addAttribute("error", "Invoice không tồn tại");
            return "boleto/demo/popcorn"; // Quay lại trang food với thông báo lỗi
        }

        InvoiceEntity invoiceEntity = invoiceEntityOpt.get();

        // Lấy TypeOfConcessionEntity từ ID món ăn
        Optional<TypeOfConcessionEntity> concessionTypeOpt = typeOfConcessionService.getAllConcessions().stream()
                .filter(type -> type.getConcessionTypeId() == concessionTypeId)
                .findFirst();

        if (concessionTypeOpt.isEmpty()) {
            model.addAttribute("error", "Loại đồ ăn không tồn tại");
            return "boleto/demo/popcorn";
        }

        TypeOfConcessionEntity concessionType = concessionTypeOpt.get();

        // Tạo mới đơn hàng và lưu vào CSDL
        ConcessionOrderEntity concessionOrderEntity = new ConcessionOrderEntity();
        concessionOrderEntity.setQuantity(quantity);
        concessionOrderEntity.setPrice(concessionType.getPrice());
        concessionOrderEntity.setConcessionType(concessionType);
        concessionOrderEntity.setInvoice(invoiceEntity);

        concessionOrderService.saveConcessionOrder(concessionOrderEntity);

        // Thêm vào giỏ hàng (session hoặc cơ sở dữ liệu)
        List<ConcessionOrderEntity> cart = (List<ConcessionOrderEntity>) model.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        cart.add(concessionOrderEntity);

        // Tính tổng tiền
        BigDecimal totalAmount = cart.stream()
                .map(order -> order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPriceWithVAT = totalAmount.add(BigDecimal.valueOf(15)); // Ví dụ thêm phí VAT 15

        // Hiển thị thông tin giỏ hàng và tổng tiền
        model.addAttribute("invoiceId", invoiceId);
        model.addAttribute("cart", cart);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("totalPriceWithVAT", totalPriceWithVAT);
        model.addAttribute("success", "Đặt hàng thành công");
        return "boleto/demo/popcorn"; // Chuyển tới trang giỏ hàng với thông tin
    }
}
