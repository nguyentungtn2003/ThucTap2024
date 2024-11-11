package com.cinema.demo.controller;

import com.cinema.demo.dto.PasswordChangeDto;
import com.cinema.demo.dto.UpdateUserDto;
import com.cinema.demo.dto.UserDto;
import com.cinema.demo.entity.UserEntity;
import com.cinema.demo.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final IUserService IUserService;

    @Autowired
    public UserController(IUserService IUserService) {
        this.IUserService = IUserService;
    }

    // Phương thức để lấy thông tin user theo email
    @GetMapping("/user/info")
    public String getUserInfo(Authentication authentication, Model model) {
        String email = authentication.getName();  // Hoặc từ userDetails nếu sử dụng custom UserDetailsService
        UserDto userDto = IUserService.findUserDtoByEmail(email);
        if (userDto != null) {
            model.addAttribute("user", userDto);
            return "user_account_infor"; // Trang hiển thị thông tin tài khoản
        } else {
            model.addAttribute("error", "User not found!");
            return "error_page"; // Nếu không tìm thấy người dùng
        }
    }

    @PostMapping("/user/update")
    public String updateUserInfo(@Valid @ModelAttribute("user") UpdateUserDto UpdateUserDto,
                                 BindingResult result,
                                 Model model) {

        // Check if there are validation errors
        if (result.hasErrors()) {
            System.out.println("Validation errors found: " + result.getAllErrors());
            model.addAttribute("user", UpdateUserDto);
            return "user_account_infor"; // Return to update form with errors displayed
        }


        if (UpdateUserDto.getDob() == null|| UpdateUserDto.getDob().toString().isEmpty()) {
            result.rejectValue("dob", null, "Ngày sinh không được để trống.");
            model.addAttribute("user", UpdateUserDto);
            return "user_account_infor";
        }

        // Update user information if no errors
        try {
            IUserService.updateUser(UpdateUserDto);  // Thực hiện cập nhật
            model.addAttribute("user", UpdateUserDto);
            model.addAttribute("success", "Update Successful"); // Thêm thông báo thành công
            return "user_account_infor"; // Trả về cùng trang sau khi cập nhật
        } catch (Exception e) {
            model.addAttribute("error", "Update failed. Please try again.");
            return "user_account_infor";
        }
    }

    @GetMapping("/user/change-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("passwordChange", new PasswordChangeDto()); // Tạo mới DTO để sử dụng trong form
        return "change-password"; // Trả về tên của template HTML để hiển thị trang
    }

    @PostMapping("/user/change-password")
    public String changePassword(@Valid @ModelAttribute("passwordChange") PasswordChangeDto passwordChangeDto,
                                 BindingResult result,
                                 Model model) {

        // Lấy email người dùng hiện tại từ SecurityContext
        String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();


        // Lấy thông tin người dùng từ cơ sở dữ liệu dựa trên email
        UserEntity userEntity = IUserService.findUserByEmail(email);
        if (userEntity == null) {
            model.addAttribute("error", "User not found.");
            return "change-password";
        }


        // So sánh mật khẩu cũ với mật khẩu trong cơ sở dữ liệu
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(passwordChangeDto.getCurrentPassword(), userEntity.getPassword())) {
            result.rejectValue("currentPassword", null, "Current password is incorrect.");
            model.addAttribute("passwordChange", passwordChangeDto);
            return "change-password";
        }

        // Kiểm tra nếu có lỗi xác thực mật khẩu mới
        if (result.hasErrors()) {
            model.addAttribute("passwordChange", passwordChangeDto);
            return "change-password";
        }

        // Mã hóa mật khẩu mới và cập nhật
        try {
            IUserService.updatePassword(userEntity, passwordChangeDto.getNewPassword());
            model.addAttribute("success", "Password changed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Could not update password. Please try again.");
            return "change-password";
        }
        return "change-password";
    }


}
