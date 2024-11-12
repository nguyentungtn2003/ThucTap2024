package com.cinema.demo.service;

import com.cinema.demo.dto.PasswordResetToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
@Service
public class PasswordResetTokenService {

    private Map<String, PasswordResetToken> tokenStore = new HashMap<>();

    // Lưu token vào bộ nhớ
    public void storeToken(String email, PasswordResetToken token) {
        tokenStore.put(token.getToken(), token); // Lưu token theo key là token, chứ không phải email
    }

    // Kiểm tra tính hợp lệ của token
    // Kiểm tra tính hợp lệ của token
    public boolean isValidResetToken(String token) {
        PasswordResetToken resetToken = tokenStore.get(token);
        if (resetToken != null) {
            System.out.println("Token Expiry: " + resetToken.getExpirationDate());
            System.out.println("Current time: " + new Date());
        }
        return resetToken != null && resetToken.getExpirationDate().after(new Date());
    }


    // Lấy email từ token
    public String getEmailByToken(String token) {
        PasswordResetToken resetToken = tokenStore.get(token);
        return resetToken != null ? resetToken.getEmail() : null;
    }

    // Xóa token sau khi sử dụng
    public void deleteToken(String token) {
        tokenStore.remove(token);
    }
}
