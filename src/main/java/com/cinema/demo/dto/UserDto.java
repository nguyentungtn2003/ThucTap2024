//<<<<<<<< HEAD:src/main/java/com/cinema/demo/dto/UserDto.java
package com.cinema.demo.dto;
//========
//package com.cinema.demo.security.form;
//>>>>>>>> refs/heads/thai:src/main/java/com/cinema/demo/security/form/UserForm.java

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto
{

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

    private Date dob;

    private String status;

    private Character sex;
//<<<<<<<< HEAD:src/main/java/com/cinema/demo/dto/UserDto.java


//    private List<String> roles;
}
//========
//}
//>>>>>>>> refs/heads/thai:src/main/java/com/cinema/demo/security/form/UserForm.java
