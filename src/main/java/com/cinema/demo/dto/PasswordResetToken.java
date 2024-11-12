package com.cinema.demo.dto;

import java.util.Date;
import java.util.UUID;

public class PasswordResetToken {

    private String token;
    private Date expirationDate;
    private String email;

    public PasswordResetToken(String email) {
        this.email = email;
        this.token = UUID.randomUUID().toString();  // Tạo token ngẫu nhiên
        this.expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60); // Token có hiệu lực trong 1 giờ

        // Log ra thời gian hiện tại và thời gian hết hạn
        System.out.println("Current time: " + new Date());
        System.out.println("Expiration time: " + this.expirationDate);
    }


    // Getters và Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
