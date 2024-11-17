package com.cinema.demo.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

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


    public String checkProperties() throws IllegalAccessException {
        for(Field f : getClass().getDeclaredFields()){
            if(f.get(this) == null){
                String[] arr = f.toString().split("\\.");
                String t = arr[arr.length - 1];
                if(t.equalsIgnoreCase("username")
                        || t.equalsIgnoreCase("password")
                        || t.equalsIgnoreCase("address")
                        || t.equalsIgnoreCase("dob")
                        || t.equalsIgnoreCase("phoneNumber")
                        || t.equalsIgnoreCase("sex")
                        || t.equalsIgnoreCase("status")
                        || t.equalsIgnoreCase("role")
                ){
                    return t;
                }
            }
        }
        return null;
    }

}