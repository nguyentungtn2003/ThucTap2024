package com.cinema.demo.controller;

import com.cinema.demo.dto.MovieDTO;
import com.cinema.demo.dto.request.MovieStatusUpdateRequest;
import com.cinema.demo.entity.MovieEntity;
import com.cinema.demo.entity.TypeEntity;
import com.cinema.demo.repository.TypeRepository;
import com.cinema.demo.service.IMovieService;
import com.cinema.demo.service.impl.MovieServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private IMovieService movieService;


    // Hiển thị chi tiết phim
    @GetMapping("/{movieId}")
    public String getMovieDetails(@PathVariable int movieId, Model model) {
        MovieDTO movie = movieService.getById(movieId);
        model.addAttribute("movie", movie);
        return "movie_detail";  // Trang chi tiết phim
    }

//    @GetMapping("/moviemanagement")
//    public String getMovies(Model model) {
//        List<MovieDTO> movies = movieService.getAllMovies();
////        System.out.println(movies);
//        model.addAttribute("movies", movies);
//        return "moviemanagement";
//    }
    @GetMapping("/moviemanagement")
    public String getMovies(@RequestParam(value = "entriesPerPage", defaultValue = "10") int entriesPerPage,
                            @RequestParam(value = "page", defaultValue = "1") int page,
                            Model model) {
        // Lấy danh sách phim với phân trang
        Page<MovieDTO> movieDTOPage = movieService.getMoviesPage(entriesPerPage, page);
        model.addAttribute("movie", new MovieDTO());

        // Thêm dữ liệu vào model để hiển thị trên view
        model.addAttribute("movies", movieDTOPage.getContent());  // Danh sách các MovieDTO
        model.addAttribute("totalPages", movieDTOPage.getTotalPages());  // Tổng số trang
        model.addAttribute("currentPage", page);  // Trang hiện tại

        return "movie_management";
    }


    // Hiển thị trang tạo phim mới
    @GetMapping("/addmovie")
    public String showAddMovieForm(@RequestParam(value = "entriesPerPage", defaultValue = "10") int entriesPerPage,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   Model model) {
        model.addAttribute("movie", new MovieDTO());
        Page<MovieDTO> movieDTOPage = movieService.getMoviesPage(entriesPerPage, page);

        // Thêm dữ liệu vào model để hiển thị trên view
        model.addAttribute("movies", movieDTOPage.getContent());  // Danh sách các MovieDTO
        model.addAttribute("totalPages", movieDTOPage.getTotalPages());  // Tổng số trang
        model.addAttribute("currentPage", page);  // Trang hiện tại
        List<TypeEntity> movieTypes = movieService.getTypeMoVie();  // Lấy tất cả loại phim
        System.out.println(movieTypes);
        model.addAttribute("movieTypes", movieTypes);  // Thêm vào model để hiển thị trên form// Thêm đối tượng MovieDTO vào model
        return "movie_management"; // Trả về view
    }

    @PostMapping("/addmovie")
    public String createMovie(@RequestParam("movieImage") MultipartFile movieImage,
                              @Valid @ModelAttribute("movie") MovieDTO movieDTO,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              @RequestParam(value = "entriesPerPage", defaultValue = "10") int entriesPerPage,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {

        Page<MovieDTO> movieDTOPage = movieService.getMoviesPage(entriesPerPage, page);

        // Thêm dữ liệu vào model để hiển thị trên view
        model.addAttribute("movies", movieDTOPage.getContent());  // Danh sách các MovieDTO
        model.addAttribute("totalPages", movieDTOPage.getTotalPages());  // Tổng số trang
        model.addAttribute("currentPage", page);  // Trang hiện tại
        if (result.hasErrors()) {
            return "movie_management";  // Nếu có lỗi, trả về lại form
        }

        // Xử lý upload hình ảnh và lưu
        if (!movieImage.isEmpty()) {
            String imageUrl = movieService.uploadImage(movieImage);
            movieDTO.setImage(imageUrl); // Set image URL vào MovieDTO
        }


        // Tạo phim mới
        movieService.createMovie(movieDTO);
        redirectAttributes.addFlashAttribute("message", "Phim đã được tạo thành công!");
        return "redirect:/movies/moviemanagement";  // Chuyển hướng đến danh sách phim
    }





    // Hiển thị trang cập nhật thông tin phim
    @GetMapping("/update/{movieId}")
    public String showUpdateMovieForm(@PathVariable int movieId, @RequestParam(value = "entriesPerPage", defaultValue = "10") int entriesPerPage,
                                      @RequestParam(value = "page", defaultValue = "1") int page,Model model) {
        Page<MovieDTO> movieDTOPage = movieService.getMoviesPage(entriesPerPage, page);

        // Thêm dữ liệu vào model để hiển thị trên view
        model.addAttribute("movies", movieDTOPage.getContent());  // Danh sách các MovieDTO
        model.addAttribute("totalPages", movieDTOPage.getTotalPages());  // Tổng số trang
        model.addAttribute("currentPage", page);  // Trang hiện tại
        MovieDTO movie = movieService.getById(movieId);
        model.addAttribute("movie", movie);
        return "movie_management";  // Trang cập nhật phim
    }

    // Cập nhật phim
    @PostMapping("/update/{movieId}")
    public String updateMovie(@PathVariable int movieId,
                              @RequestParam("movieImage") MultipartFile movieImage,
                              @Valid @ModelAttribute MovieDTO movieDTO,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "movie_management";  // Nếu có lỗi, trả về lại form
        }

        // Nếu có ảnh mới, xử lý upload ảnh
        if (!movieImage.isEmpty()) {
            String imageUrl = movieService.uploadImage(movieImage);
            movieDTO.setImage(imageUrl); // Gán URL ảnh vào movieDTO
        }

        movieDTO.setMovieId(movieId);
        movieService.updateMovie(movieDTO);
        redirectAttributes.addFlashAttribute("message", "Phim đã được cập nhật thành công!");
        return "redirect:/movies/moviemanagement";  // Chuyển hướng đến danh sách phim
    }
   //update status
   @PostMapping("/updateStatus")
   @ResponseBody
   public ResponseEntity<Void> updateMovieStatus(@RequestBody MovieStatusUpdateRequest request) {
       Optional<MovieEntity> movieOptional = movieService.findById(request.getMovieId());

       if (movieOptional.isPresent()) {
           MovieEntity movie = movieOptional.get();
           movie.setIsShowing(request.getIsShowing());  // Cập nhật trạng thái phim
           movieService.save(movie);  // Lưu lại thay đổi vào cơ sở dữ liệu
           return ResponseEntity.ok().build();  // Trả về phản hồi thành công
       } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Nếu không tìm thấy phim
       }
   }

    // Xóa phim
    @GetMapping("/delete/{movieId}")
    public String deleteMovie(@PathVariable int movieId, RedirectAttributes redirectAttributes) {
        try {
            movieService.deleteMovie(movieId);
            redirectAttributes.addFlashAttribute("message", "Phim đã được xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy phim để xóa.");
        }
        return "redirect:/movies/moviemanagement";  // Chuyển hướng đến danh sách phim
    }

    // Hiển thị danh sách các phim với phân trang
    @GetMapping
    public String getAllMovies(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size); // Mỗi trang hiển thị 10 phim
        Page<MovieDTO> moviePage = (Page<MovieDTO>) movieService.getAllMovies(pageable);
        model.addAttribute("movies", moviePage);
        return "movie_list";  // Trang danh sách phim
    }
}
