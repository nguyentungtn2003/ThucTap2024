package com.cinema.demo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSenderImpl mailSender;

    // Inject JavaMailSenderImpl qua constructor
    public EmailService(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    // Gửi email với liên kết reset mật khẩu
    public void sendResetPasswordLink(String to, String token) {
        String subject = "Yêu cầu Reset Mật Khẩu";
        // Tạo nội dung email với URL chứa token
        String content = "Chúng tôi đã nhận được yêu cầu reset mật khẩu cho tài khoản của bạn. "
                + "Nếu bạn không yêu cầu reset mật khẩu, hãy bỏ qua email này. "
                + "Nhấp vào liên kết sau để reset mật khẩu: "
                + "http://localhost:8080/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            // Gửi email
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();  // Log lỗi nếu có vấn đề trong việc gửi email
        }
    }
}
