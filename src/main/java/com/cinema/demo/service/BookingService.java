package com.cinema.demo.service;

import com.cinema.demo.dto.BooKingDetailsDTO;
import com.cinema.demo.entity.BookingEntity;
import com.cinema.demo.entity.MovieEntity;
import com.cinema.demo.repository.BookingRepository;
import com.cinema.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MovieRepository movieRepository;

    public Double getTotalAmount() {
        // Lấy ngày đầu tháng và cuối tháng
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfMonth = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().toLocalDate().lengthOfMonth())
                .withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        // Chuyển đổi sang Date
        Date startDate = Date.from(startOfMonth.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfMonth.atZone(ZoneId.systemDefault()).toInstant());

        // Tìm các booking trong khoảng thời gian của tháng
        List<BookingEntity> allBooking = bookingRepository.findAllByCreatedDateTimeBetween(startDate, endDate);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (BookingEntity bookingEntity : allBooking) {
            //tính tổng doanh thu
            totalAmount = totalAmount.add(bookingEntity.getBookingFee());
        }
        return totalAmount.doubleValue();
    }

    public String formatTotalAmount(Double totalAmount) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return formatter.format(totalAmount);
    }

    public String getMonthYear() {

        // Lấy tháng và năm hiện tại
        YearMonth currentMonth = YearMonth.now();

        // Định dạng tháng và năm theo kiểu "MM-yyyy"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");

        // Trả về chuỗi theo định dạng và thêm "Tháng" và "Năm"
        return currentMonth.format(formatter).substring(0, 2) + " " + currentMonth.format(formatter).substring(3); // Ví dụ: "Tháng 11, Năm 2024"
    }

    public List<BooKingDetailsDTO> getAllBookingDetails() {
        // Lấy ngày đầu tháng và cuối tháng
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfMonth = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().toLocalDate().lengthOfMonth())
                .withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        // Chuyển đổi sang Date
        Date startDate = Date.from(startOfMonth.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfMonth.atZone(ZoneId.systemDefault()).toInstant());

        // Tìm các booking trong khoảng thời gian của tháng
        List<BookingEntity> allBooking = bookingRepository.findAllByCreatedDateTimeBetween(startDate, endDate);
        List<MovieEntity> allMovie = movieRepository.findAllByReleaseDateBetween(startDate, endDate);

        // Tạo Map để tra cứu MovieEntity theo ID
        Map<Integer, MovieEntity> movieMap = allMovie.stream()
                .collect(Collectors.toMap(MovieEntity::getMovieId, movie -> movie));

        // Kết hợp dữ liệu và tạo DTO
        return allBooking.stream()
                .map(booking -> {
                    // Lấy thông tin MovieEntity từ Map
                    MovieEntity movie = movieMap.get(booking.getBookingId());

                    // Trả về DTO
                    return new BooKingDetailsDTO(
                            booking.getBookingId(),
                            movie != null ? movie.getMovieName() : "Unknown", // Tên phim, hoặc "Unknown" nếu không tìm thấy
                            booking.getBookingFee()
                    );
                })
                .sorted((dto1, dto2) -> {
                    BigDecimal amount1 = dto1.getTotalAmount() != null ? dto1.getTotalAmount() : BigDecimal.ZERO;
                    BigDecimal amount2 = dto2.getTotalAmount() != null ? dto2.getTotalAmount() : BigDecimal.ZERO;
                    return amount2.compareTo(amount1); // Sắp xếp giảm dần
                })
                .collect(Collectors.toList());
    }

    public String getFormattedTotalAmountByMonthAndYear(int month, int year) {
        Double totalAmount = getTotalAmountByMonthAndYear(month, year);
        return formatTotalAmount(totalAmount);
    }

    public Double getTotalAmountByMonthAndYear(int month, int year) {
        // Lấy ngày đầu tháng và cuối tháng
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.toLocalDate().lengthOfMonth())
                .withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        // Chuyển đổi sang Date
        Date startDate = Date.from(startOfMonth.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfMonth.atZone(ZoneId.systemDefault()).toInstant());

        // Tìm các booking trong khoảng thời gian của tháng
        List<BookingEntity> allBooking = bookingRepository.findAllByCreatedDateTimeBetween(startDate, endDate);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (BookingEntity bookingEntity : allBooking) {
            // Tính tổng doanh thu
            totalAmount = totalAmount.add(bookingEntity.getBookingFee());
        }
        return totalAmount.doubleValue();
    }

    public List<BooKingDetailsDTO> getAllBookingDetailsByMonthYear(int month, int year) {
        // Kiểm tra tính hợp lệ của tháng và năm
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Tháng phải nằm trong khoảng từ 1 đến 12.");
        }
        if (year < 1900 || year > LocalDate.now().getYear()) {
            throw new IllegalArgumentException("Năm không hợp lệ.");
        }

        // Tính toán ngày đầu tháng và cuối tháng dựa trên tham số tháng và năm
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.toLocalDate().lengthOfMonth())
                .withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        // Chuyển đổi sang Date
        Date startDate = Date.from(startOfMonth.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfMonth.atZone(ZoneId.systemDefault()).toInstant());

        // Tìm các booking trong khoảng thời gian của tháng
        List<BookingEntity> allBooking = bookingRepository.findAllByCreatedDateTimeBetween(startDate, endDate);
        List<MovieEntity> allMovie = movieRepository.findAllByReleaseDateBetween(startDate, endDate);

        // Tạo Map để tra cứu MovieEntity theo ID
        Map<Integer, MovieEntity> movieMap = allMovie.stream()
                .collect(Collectors.toMap(MovieEntity::getMovieId, movie -> movie));

        // Kết hợp dữ liệu và tạo DTO
        return allBooking.stream()
                .map(booking -> {
                    // Lấy thông tin MovieEntity từ Map
                    MovieEntity movie = movieMap.get(booking.getBookingId());

                    // Trả về DTO
                    return new BooKingDetailsDTO(
                            booking.getBookingId(),
                            movie != null ? movie.getMovieName() : "Unknown", // Tên phim, hoặc "Unknown" nếu không tìm thấy
                            booking.getBookingFee()
                    );
                })
                .sorted((dto1, dto2) -> {
                    BigDecimal amount1 = dto1.getTotalAmount() != null ? dto1.getTotalAmount() : BigDecimal.ZERO;
                    BigDecimal amount2 = dto2.getTotalAmount() != null ? dto2.getTotalAmount() : BigDecimal.ZERO;
                    return amount2.compareTo(amount1); // Sắp xếp giảm dần
                })
                .collect(Collectors.toList());
    }



}
