package com.cinema.demo.controller;

import com.cinema.demo.dto.ShowtimeDTO;
import com.cinema.demo.entity.CinemaRoomEntity;
import com.cinema.demo.entity.MovieEntity;
import com.cinema.demo.entity.ShowDateEntity;
import com.cinema.demo.entity.ShowtimeEntity;
import com.cinema.demo.repository.CinemaRoomRepository;
import com.cinema.demo.repository.MovieRepository;
import com.cinema.demo.repository.ShowDateRepository;
import com.cinema.demo.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/showtime-management")
@Transactional
public class ShowtimeController {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private CinemaRoomRepository cinemaRoomRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ShowDateRepository showDateRepository;



    //Xem suất chiếu
    @GetMapping("/showtime")
    public ResponseEntity<List<ShowtimeEntity>> getShowtime(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ShowtimeEntity> showtimePage = showtimeRepository.findAll(pageable);

        System.out.println("Total Showtimes: " + showtimePage.getTotalElements());

        return ResponseEntity.ok(showtimePage.getContent());
    }

    // Thêm suất chiếu mới
    @PostMapping("/showtime")
    public ResponseEntity<String> addShowtime(
            @RequestParam int movieId,
            @RequestParam int cinemaRoomId,
            @RequestParam String showDate, // Nhận ngày chiếu
            @RequestParam String showTimes) {

        // Tìm phòng chiếu và phim
        CinemaRoomEntity cinemaRoom = cinemaRoomRepository.findById(cinemaRoomId).orElse(null);
        MovieEntity movie = movieRepository.findById(movieId).orElse(null);

        if (cinemaRoom == null || movie == null) {
            return ResponseEntity.badRequest().body("Phòng chiếu hoặc phim không tồn tại.");
        }

        // Chuyển đổi chuỗi ngày chiếu thành LocalDate
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(showDate.trim());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Định dạng ngày chiếu không hợp lệ: " + showDate);
        }

        // Tìm hoặc tạo mới ShowDateEntity
        ShowDateEntity showDateEntity = showDateRepository.findByStartDate(parsedDate)
                .orElseGet(() -> {
                    ShowDateEntity newShowDate = new ShowDateEntity();
                    newShowDate.setStartDate(parsedDate); // Gán giá trị startDate
                    return showDateRepository.save(newShowDate);
                });

        // Xử lý showTimes
        String[] times = showTimes.split(",");
        for (String time : times) {
            try {
                LocalTime parsedTime = LocalTime.parse(time.trim()); // Chuyển chuỗi thành LocalTime

                // Tạo mới ShowtimeEntity
                ShowtimeEntity showtime = new ShowtimeEntity();
                showtime.setCinemaRoom(cinemaRoom);
                showtime.setMovie(movie);
                showtime.setShowDate(showDateEntity); // Gán ngày chiếu
                showtime.setStartTime(parsedTime);   // Gán giờ chiếu

                // Lưu suất chiếu
                showtimeRepository.save(showtime);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Định dạng thời gian không hợp lệ: " + time);
            }
        }

        return ResponseEntity.ok("Thêm suất chiếu thành công.");
    }




    //Cập nhật suất chiếu
    @PutMapping("/showtime/{id}")
    public ResponseEntity<String> updateShowtime(@PathVariable int id, @RequestBody ShowtimeDTO showtimeDTO) {
        ShowtimeEntity showtime = showtimeRepository.findById(id).orElse(null);

        if (showtime == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy suất chiếu.");
        }

        CinemaRoomEntity cinemaRoom = cinemaRoomRepository.findById(showtimeDTO.getCinemaRoomId()).orElse(null);
        MovieEntity movie = movieRepository.findById(showtimeDTO.getMovieId()).orElse(null);

        if (cinemaRoom == null || movie == null) {
            return ResponseEntity.badRequest().body("Phòng chiếu hoặc phim không tồn tại.");
        }

        showtime.setCinemaRoom(cinemaRoom);
        showtime.setMovie(movie);

        // Cập nhật thời gian chiếu (startTime)
        if (showtimeDTO.getStartTime() == null || showtimeDTO.getStartTime().isEmpty()) {
            return ResponseEntity.badRequest().body("Thời gian chiếu không hợp lệ.");
        }
        try {
            LocalTime parsedTime = LocalTime.parse(showtimeDTO.getStartTime());
            showtime.setStartTime(parsedTime);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Định dạng giờ chiếu không đúng.");
        }

        showtimeRepository.save(showtime);
        return ResponseEntity.ok("Cập nhật suất chiếu thành công.");
    }
    //Xóa suất chiếu
    @DeleteMapping("/showtime/{id}")
    public ResponseEntity<String> deleteShowtime(@PathVariable int id) {
        ShowtimeEntity showtime = showtimeRepository.findById(id).orElse(null);

        if (showtime == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy suất chiếu.");
        }

        // Xóa suất chiếu và các ngày chiếu liên quan
        showDateRepository.deleteByShowtime_ShowtimeId(id);
        showtimeRepository.delete(showtime);

        return ResponseEntity.ok("Xóa suất chiếu thành công.");
    }

    // API lấy danh sách phim
    @GetMapping("/movies")
    public ResponseEntity<List<MovieEntity>> getMovies() {
        List<MovieEntity> movies = movieRepository.findAll();
        return ResponseEntity.ok(movies);
    }

    // API lấy danh sách phòng chiếu
    @GetMapping("/cinema-rooms")
    public ResponseEntity<List<CinemaRoomEntity>> getCinemaRooms() {
        List<CinemaRoomEntity> cinemaRooms = cinemaRoomRepository.findAll();
        return ResponseEntity.ok(cinemaRooms);
    }

    @GetMapping("/showtime/{id}")
    public ResponseEntity<ShowtimeEntity> getShowtimeById(@PathVariable int id) {
        Optional<ShowtimeEntity> showtime = showtimeRepository.findById(id);
        if (showtime.isPresent()) {
            return ResponseEntity.ok(showtime.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
