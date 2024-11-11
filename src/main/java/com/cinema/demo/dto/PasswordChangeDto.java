package com.cinema.demo.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeDto {
    private Long id;
    @NotEmpty(message = "Mật khẩu hiện tại không được để trống")
    private String currentPassword;

    @NotEmpty(message = "Mật khẩu mới không được để trống")
    @Size(min = 8, message = "Mật khẩu mới phải có ít nhất 8 ký tự")
    @Pattern(regexp = ".*[!@#$%^&*(),.?\":{}|<>].*", message = "Mật khẩu mới phải chứa ít nhất một ký tự đặc biệt")
    private String newPassword;

    @NotEmpty(message = "Xác nhận mật khẩu không được để trống")
    private String confirmNewPassword;
}