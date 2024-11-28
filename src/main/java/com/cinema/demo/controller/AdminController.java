    package com.cinema.demo.controller;

    import com.cinema.demo.dto.BooKingDetailsDTO;
    import com.cinema.demo.dto.RoomDetailDTO;
    import com.cinema.demo.entity.CinemaRoomDetailEntity;
    import com.cinema.demo.entity.CinemaRoomEntity;
    import com.cinema.demo.entity.RoleEntity;
    import com.cinema.demo.entity.UserEntity;
    import com.cinema.demo.repository.RoleRepository;
    import com.cinema.demo.repository.UserRepository;
    import com.cinema.demo.service.BookingService;
    import com.cinema.demo.service.CinemaRoomDetailService;
    import com.cinema.demo.service.ManagerRoomService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.domain.Sort;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Controller;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import java.sql.Date;
    import java.util.*;

    @Controller
    @RequestMapping("/admins")
    public class AdminController {
        @Autowired
        private BookingService bookingService;

        @Autowired
        private ManagerRoomService managerRoomService;

        @Autowired
        private CinemaRoomDetailService cinemaRoomDetailService;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private BCryptPasswordEncoder passwordEncoder; // Để mã hóa mật khẩu

//        // Trang chủ admin
//        @GetMapping
//        public String getAdminHome() {
//            return "/admin/admin";
//        }

        // Trang chủ admin
        @GetMapping
        public String getAdminHomes(Model model) {
            model.addAttribute("totalAmount", bookingService.formatTotalAmount(bookingService.getTotalAmount()));
            model.addAttribute("startDate", bookingService.getMonthYear());
            model.addAttribute("allBookingDetails", bookingService.getAllBookingDetails());

            return "/admin/ad1_mainboard";
        }

        // cập nhật doanh thu khi chọn tháng khác
        @GetMapping("/revenue")
        public ResponseEntity<Map<String, Object>> getRevenueByMonthAndYear(
                @RequestParam("month") int month,
                @RequestParam("year") int year) {
            try {
                // Lấy tổng doanh thu
                Double totalAmount = bookingService.getTotalAmountByMonthAndYear(month, year);

                // Lấy danh sách chi tiết booking
                List<BooKingDetailsDTO> bookingDetails = bookingService.getAllBookingDetailsByMonthYear(month, year);

                // Tạo response
                Map<String, Object> response = new HashMap<>();
                response.put("totalAmount", totalAmount);
                response.put("bookingDetails", bookingDetails);

                return ResponseEntity.ok(response);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
            }
        }

        //Quản lý Phòng chiếu
        @GetMapping("/room-management")
        public String roomManagement(Model model) {
            model.addAttribute("allInfoRoom", managerRoomService.getAllInfoRoom());
            return "admin/ad1_room";
        }

        //quản lý vật tư phòng chiếu
        @GetMapping("/room-management/details/{idRoom}")
        public String roomManagementDetails(@PathVariable int idRoom, Model model) {
            model.addAttribute("allInfoRoomDetails", cinemaRoomDetailService.getCinemaRoomDetailById(idRoom));
            model.addAttribute("cinemaRoom", cinemaRoomDetailService.getCinemaRoom(idRoom));
            return "admin/ad1_room_detail";
        }

        //lưu ghi chú vật tư mới
        @PostMapping("/room-management/details/insertNote")
        public ResponseEntity<CinemaRoomDetailEntity> addRoomDetail(@RequestBody RoomDetailDTO roomDetailDTO) {
            // Lấy thông tin phòng chiếu
            CinemaRoomEntity cinemaRoom = cinemaRoomDetailService.getCinemaRoom(roomDetailDTO.getIdCinemaRoom());
            if (cinemaRoom == null) {
                return ResponseEntity.badRequest().build(); // Phòng chiếu không tồn tại
            }
            // Tạo đối tượng CinemaRoomDetailEntity từ DTO
            CinemaRoomDetailEntity newRoomDetail = new CinemaRoomDetailEntity();
            newRoomDetail.setChair(roomDetailDTO.getChair());
            newRoomDetail.setMovieProjector(roomDetailDTO.getMovieProjector());
            newRoomDetail.setLoudspeaker(roomDetailDTO.getLoudspeaker());
            newRoomDetail.setLed(roomDetailDTO.getLed());
            newRoomDetail.setNote(roomDetailDTO.getNote());
            newRoomDetail.setStatus(roomDetailDTO.getStatus());
            newRoomDetail.setRoom(cinemaRoom);

            // Lưu vào database
            CinemaRoomDetailEntity savedRoomDetail = cinemaRoomDetailService.saveRoomDetail(newRoomDetail);
            return ResponseEntity.ok(savedRoomDetail); // Trả về thông tin vừa lưu
        }

        //chỉnh sửa roomdetail
        @PutMapping("/room-management/details/edit/{id}")
        public ResponseEntity<?> updateRoomDetail(
                @PathVariable Integer id,
                @RequestBody CinemaRoomDetailEntity updatedRoomDetail) {
            Optional<CinemaRoomDetailEntity> existingDetail = cinemaRoomDetailService.findRoomDetailById(id);

            if (existingDetail.isPresent()) {
                CinemaRoomDetailEntity detail = existingDetail.get();

                detail.setChair(updatedRoomDetail.getChair());
                detail.setMovieProjector(updatedRoomDetail.getMovieProjector());
                detail.setLoudspeaker(updatedRoomDetail.getLoudspeaker());
                detail.setLed(updatedRoomDetail.getLed());
                detail.setNote(updatedRoomDetail.getNote());
                detail.setStatus(updatedRoomDetail.getStatus());

                cinemaRoomDetailService.saveRoomDetail(detail);

                return ResponseEntity.ok("Updated successfully");
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room detail not found");
        }

        //xóa room detail
        @DeleteMapping("/room-management/details/delete/{id}")
        public ResponseEntity<?> deleteRoomDetail(@PathVariable Integer id) {
            Optional<CinemaRoomDetailEntity> roomDetail = cinemaRoomDetailService.findRoomDetailById(id);

            if (roomDetail.isPresent()) {
                cinemaRoomDetailService.deleteById(id); // Xóa theo ID
                return ResponseEntity.ok("Room Detail đã được xóa.");
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room Detail không tồn tại.");
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

//            // Kiểm tra nếu vai trò ROLE_STAFF chưa tồn tại thì thêm vào database
//            RoleEntity staffRole = roleRepository.findByRoleName("ROLE_STAFF");
//            if (staffRole == null) {
//                staffRole = new RoleEntity();
//                staffRole.setRoleName("ROLE_STAFF");
//                staffRole = roleRepository.save(staffRole); // Lưu ROLE_STAFF vào database
//            }

            // Tạo mới nhân viên và gán vai trò ROLE_STAFF
            UserEntity newStaff = new UserEntity();
            newStaff.setEmail(email);
            newStaff.setName(fullName);
            newStaff.setPassword(passwordEncoder.encode(password)); // Mã hóa mật khẩu
            newStaff.setPhoneNumber(phoneNumber);
            newStaff.setAddress(address);
            newStaff.setDob(dob);
            newStaff.setSex(sex);
            newStaff.setStatus(status);
//            newStaff.setRoles(List.of(staffRole)); // Gán vai trò STAFF cho nhân viên mới
            RoleEntity userRole = roleRepository.findByRoleName("STAFF");
            newStaff.setRoles(new HashSet<>());
            newStaff.getRoles().add(userRole);

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
            staff.setName(fullName);
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


