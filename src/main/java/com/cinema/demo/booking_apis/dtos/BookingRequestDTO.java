package com.cinema.demo.booking_apis.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BookingRequestDTO {
    private Long userId; // Đổi sang kiểu Long để khớp với UserEntity
    private Integer showtimeId; // Thay vì scheduleId, dùng showtimeId theo cấu trúc hiện tại
    private List<Integer> seatIds; // Giữ tên dễ hiểu và logic hơn là listSeatIds
    private BigDecimal bookingFee; // Thêm phí đặt vé
    private String status; // Thêm trạng thái cho yêu cầu đặt vé
}
