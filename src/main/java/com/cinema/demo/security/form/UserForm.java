package com.cinema.demo.security.form;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Min 3 Characters is required")
    private String name;

    @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = ".*[!@#$%^&*(),.?\":{}|<>].*", message = "Password must contain at least one special character")
    private String password;

//    @NotBlank(message = "About is required")
//    private String about;
    @Size(min = 8, max = 12, message = "Invalid Phone Number")
    @NotBlank
    @Pattern(regexp = "^0\\d{9}$", message = "Phone number must start with 0 and have exactly 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Address should not be empty")
    private String address;

    @NotNull(message = "Date of birth should not be empty")
    @Past(message = "Date of birth cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    private String status;

    @NotBlank(message = "Gender should not be empty")
    private Character sex;
}
