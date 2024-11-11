package com.cinema.demo.dto;

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
public class UpdateUserDto {

    private Long id;

    @NotEmpty(message = "Phone number should not be empty")
    @Pattern(regexp = "^0\\d{9}$", message = "Phone number must start with 0 and have exactly 10 digits")
    private String phoneNumber;

    @NotEmpty(message = "Address should not be empty")
    private String address;

    private Date dob;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be in a valid format (e.g. example@gmail.com)")
    private String email;

    private String status;

    @NotEmpty(message = "Full name should not be empty")
    private String fullName;

    private Character sex;

    private List<String> roles;
}
