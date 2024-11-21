    package com.cinema.demo.controller;

    import com.cinema.demo.entity.RoleEntity;
    import com.cinema.demo.entity.UserEntity;
    import com.cinema.demo.repository.RoleRepository;
    import com.cinema.demo.repository.UserRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.domain.Sort;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Controller;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.bind.annotation.*;

    import java.sql.Date;
    import java.util.List;
    import java.util.Optional;
    import java.util.stream.Collectors;

    @Controller
    @RequestMapping("/admin")
    public class AdminController {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private BCryptPasswordEncoder passwordEncoder; // Để mã hóa mật khẩu

        // Trang chủ admin
        @GetMapping
        public String getAdminHome() {
            return "/admin/admin";
        }

        // Quản lý nhân viên
        @GetMapping("/staff-management")
        public String staffManagement() {
            return "admin/staff-management";
        }
        //Quản lý suất chiếu
        @GetMapping("/showtime-management")
        public String showtimeManagement() {
            return "admin/showtime-management";
        }

        // 1. Lấy danh sách nhân viên
        @GetMapping("/staff")
        public ResponseEntity<Page<UserEntity>> getStaffList(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "4") int size,
                @RequestParam(defaultValue = "id") String sortBy) {

            RoleEntity staffRole = roleRepository.findByRoleName("ROLE_STAFF");
            if (staffRole == null) {
                return ResponseEntity.badRequest().body(null);
            }

            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<UserEntity> staffPage = userRepository.findByRolesContaining(staffRole, pageable);

            return ResponseEntity.ok(staffPage);
        }

        // 2. Thêm nhân viên mới với đầy đủ các trường
        @PostMapping("/staff")
        public ResponseEntity<String> addStaff(
                @RequestParam String email,
                @RequestParam String fullName,
                @RequestParam String password,
                @RequestParam String phoneNumber,
                @RequestParam String address,
                @RequestParam Date dob,
                @RequestParam char sex,
                @RequestParam String status) {

            // Kiểm tra xem email đã tồn tại chưa
            UserEntity existingUser = userRepository.findByEmail(email);
            if (existingUser != null) {
                return ResponseEntity.badRequest().body("Email đã tồn tại.");
            }

            // Kiểm tra nếu vai trò ROLE_STAFF chưa tồn tại thì thêm vào database
            RoleEntity staffRole = roleRepository.findByRoleName("ROLE_STAFF");
            if (staffRole == null) {
                staffRole = new RoleEntity();
                staffRole.setRoleName("ROLE_STAFF");
                staffRole = roleRepository.save(staffRole); // Lưu ROLE_STAFF vào database
            }

            // Tạo mới nhân viên và gán vai trò ROLE_STAFF
            UserEntity newStaff = new UserEntity();
            newStaff.setEmail(email);
            newStaff.setFullName(fullName);
            newStaff.setPassword(passwordEncoder.encode(password)); // Mã hóa mật khẩu
            newStaff.setPhoneNumber(phoneNumber);
            newStaff.setAddress(address);
            newStaff.setDob(dob);
            newStaff.setSex(sex);
            newStaff.setStatus(status);
            newStaff.setRoles(List.of(staffRole)); // Gán vai trò STAFF cho nhân viên mới

            userRepository.save(newStaff);
            return ResponseEntity.ok("Thêm nhân viên thành công.");
        }

        // 3. Cập nhật thông tin nhân viên
        @PutMapping("/staff/{id}")
        public ResponseEntity<String> updateStaff(
                @PathVariable Long id,
                @RequestParam String fullName,
                @RequestParam String phoneNumber,
                @RequestParam String address,
                @RequestParam Date dob,
                @RequestParam char sex,
                @RequestParam String status) {

            Optional<UserEntity> optionalStaff = userRepository.findById(id);

            if (optionalStaff.isEmpty()) {
                return ResponseEntity.badRequest().body("Không tìm thấy nhân viên.");
            }

            UserEntity staff = optionalStaff.get();
            staff.setFullName(fullName);
            staff.setPhoneNumber(phoneNumber);
            staff.setAddress(address);
            staff.setDob(dob);
            staff.setSex(sex);
            staff.setStatus(status);

            userRepository.save(staff);
            return ResponseEntity.ok("Cập nhật thông tin nhân viên thành công.");
        }

        // 4. Xóa nhân viên

        @Transactional
        @DeleteMapping("/staff/{id}")
        public ResponseEntity<String> deleteStaff(@PathVariable("id") Long userId) {
            if (userId == null) {
                return ResponseEntity.badRequest().body("ID nhân viên không được để trống.");
            }

            Optional<UserEntity> userOptional = userRepository.findById(userId);

            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Không tìm thấy nhân viên.");
            }

            UserEntity user = userOptional.get();
            user.getRoles().clear(); // Xóa các vai trò liên kết của người dùng
            userRepository.save(user); // Lưu để cập nhật thay đổi

            userRepository.delete(user); // Bây giờ có thể xóa người dùng
            return ResponseEntity.ok("Xóa nhân viên thành công.");
        }

        @GetMapping("/staff/{id}")
        public ResponseEntity<UserEntity> getStaffById(@PathVariable Long id) {
            Optional<UserEntity> staff = userRepository.findById(id);
            if (staff.isPresent()) {
                return ResponseEntity.ok(staff.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }


