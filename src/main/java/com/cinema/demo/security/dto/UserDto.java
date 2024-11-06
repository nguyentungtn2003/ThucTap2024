package com.cinema.demo.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    @NotEmpty
    @Pattern(regexp = "^0\\d{9}$", message = "Phone number must start with 0 and have exactly 10 digits")
    private String phoneNumber;

    @NotEmpty(message = "Address should not be empty")
    private String address;

    private Date dob;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be in a valid format (e.g. example@gmail.com)")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = ".*[!@#$%^&*(),.?\":{}|<>].*", message = "Password must contain at least one special character")
    private String password;

    private String status;

    @NotEmpty(message = "Full name should not be empty")
    private String fullName;

    private Character sex;

    private List<String> roles;
}