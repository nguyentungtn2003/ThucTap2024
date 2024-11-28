package com.cinema.demo.controller;

import com.cinema.demo.dto.TicketConfirmationDTO;
import com.cinema.demo.dto.UserDto;
import com.cinema.demo.entity.NotificationEntity;
import com.cinema.demo.entity.UserEntity;
import com.cinema.demo.service.IUserService;
import com.cinema.demo.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class NotificationController {

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private IUserService userService;

    // API gửi thông báo vé thành công
    @GetMapping("/ticket-success/{ticketId}")
    public ResponseEntity<TicketConfirmationDTO> sendNotification(@PathVariable int ticketId) {
        TicketConfirmationDTO confirmationDTO = notificationService.generateConfirmation(ticketId);
        if (confirmationDTO != null) {
            return ResponseEntity.ok(confirmationDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // API lấy danh sách thông báo chưa đọc
    @GetMapping("/notification")
    public ResponseEntity<List<NotificationEntity>> getUnreadNotifications(@RequestParam Long id) {
        try {
            UserEntity user = new UserEntity();
            user.setId(id);

            List<NotificationEntity> notifications = notificationService.getUnreadNotifications(user);

            if (notifications.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(notifications); // Trả về HTTP 204 nếu không có thông báo
            } else {
                return ResponseEntity.ok(notifications); // Trả về danh sách thông báo
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Trả về HTTP 500 nếu có lỗi
        }
    }

    // API lấy thông tin người dùng
    @GetMapping("/notification/info")
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        try {
            // Kiểm tra authentication có null không
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Người dùng chưa đăng nhập hoặc phiên làm việc đã hết hạn.");
            }

            // Lấy email từ authentication
            String email = authentication.getName();
            System.out.println("Authenticated email: " + email); // Debug email

            // Tìm thông tin người dùng qua email
            UserDto userDto = userService.findUserDtoByEmail(email);

            // Kiểm tra userDto có null không
            if (userDto != null) {
                return ResponseEntity.ok(userDto); // Trả về thông tin người dùng nếu tìm thấy
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy thông tin người dùng với email: " + email);
            }
        } catch (Exception e) {
            // Log lỗi và trả về HTTP 500
            e.printStackTrace(); // Debug lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi khi xử lý yêu cầu.");
        }
    }

}
