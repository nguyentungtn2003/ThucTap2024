package com.cinema.demo.controller;

import com.cinema.demo.dto.PasswordResetToken;
import com.cinema.demo.service.EmailService;
import com.cinema.demo.service.IUserService;
import com.cinema.demo.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
@Controller
public class ResetPasswordController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenService tokenService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Trang nhập email để yêu cầu reset mật khẩu
    @GetMapping("/request-reset-password")
    public String showResetRequestForm() {
        return "reset-password-request";  // Đây là trang yêu cầu nhập email
    }

    // Trang thực hiện reset mật khẩu
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        // Kiểm tra token có hợp lệ không
        if (tokenService.isValidResetToken(token)) {
            model.addAttribute("token", token);
            return "reset-password";  // Trang nhập mật khẩu mới
        } else {
            model.addAttribute("error", "Token không hợp lệ hoặc đã hết hạn.");
            return "error";  // Nếu token không hợp lệ
        }
    }

    // Xử lý yêu cầu reset mật khẩu (gửi email xác nhận)
    @PostMapping("/request-reset-password")
    public String requestResetPassword(@RequestParam("email") String email, Model model) {
        if (iUserService.existsByEmail(email)) {
            // Tạo token reset mật khẩu và lưu vào bộ nhớ
            PasswordResetToken token = new PasswordResetToken(email);
            tokenService.storeToken(email, token);

            // Gửi email yêu cầu reset mật khẩu
            emailService.sendResetPasswordLink(email, token.getToken());

            // Thông báo gửi email thành công
            String successMessage = "Đã gửi email đến bạn. Vui lòng kiểm tra hộp thư của bạn.";

            // Mã hóa thông báo để tránh ký tự đặc biệt trong URL
            String encodedMessage = URLEncoder.encode(successMessage, StandardCharsets.UTF_8);

            // Redirect và truyền thông báo thành công vào URL
            return "redirect:/request-reset-password?message=" + encodedMessage;
        } else {
            model.addAttribute("error", "Email không tồn tại.");
            return "reset-password-request";  // Nếu email không tồn tại, quay lại trang yêu cầu reset mật khẩu
        }
    }


    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword, Model model) {
        // Kiểm tra token có hợp lệ không
        if (tokenService.isValidResetToken(token)) {
            // Lấy email từ token
            String email = tokenService.getEmailByToken(token);
            if (email != null) {
                // Kiểm tra mật khẩu mới có hợp lệ không
                if (newPassword != null && !newPassword.isEmpty()) {
                    // Mã hóa mật khẩu mới trước khi lưu
                    String encodedPassword = passwordEncoder.encode(newPassword);

                    // Cập nhật mật khẩu
                    boolean result = iUserService.resetPassword(email, encodedPassword);
                    if (result) {
                        tokenService.deleteToken(token);  // Xóa token sau khi sử dụng
                        return "redirect:/login?message=Password%20has%20been%20reset"; // Thành công
                    } else {
                        model.addAttribute("error", "Không thể cập nhật mật khẩu.");
                        return "error";  // Nếu không thành công
                    }
                } else {
                    model.addAttribute("error", "Mật khẩu mới không hợp lệ.");
                    return "error";  // Nếu mật khẩu mới không hợp lệ
                }
            } else {
                model.addAttribute("error", "Không tìm thấy email từ token.");
                return "error";  // Nếu không tìm thấy email
            }
        }
        model.addAttribute("error", "Token không hợp lệ hoặc đã hết hạn.");
        return "error";  // Nếu token không hợp lệ
    }

}
