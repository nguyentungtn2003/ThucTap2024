package com.cinema.demo.service.impl;

import com.cinema.demo.repository.ShowtimeRepository;
import com.cinema.demo.dto.MovieDTO;
import com.cinema.demo.entity.MovieEntity;
import com.cinema.demo.entity.MovieTypeEntity;
import com.cinema.demo.entity.TypeEntity;
import com.cinema.demo.repository.MovieRepository;
import com.cinema.demo.repository.MovieTypeRepository;
import com.cinema.demo.repository.TypeRepository;
import com.cinema.demo.service.IMovieService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements IMovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private MovieTypeRepository movieTypeRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    private static String UPLOAD_DIR = "D:\\java\\ProjectOJT\\ThucTap2024\\upload/";
    private final String DIR = "upload/";

    // Lấy tất cả phim
    @Override
    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
//    @Override
//    public List<MovieDTO> getAllMoviesIsShowing() {
//        return movieRepository.findAll().stream()
//                .filter(movie -> movie.getIsShowing() == 1)  // Lọc phim có isShowing == 1
//                .map(this::convertToDTO)  // Chuyển đổi thành MovieDTO
//                .collect(Collectors.toList());
//    }
    @Override
    public List<MovieDTO> getAllMoviesIsShowing() {
        LocalDate today = LocalDate.now();

        // Chuyển đổi ngày hôm nay thành Date
        Date endOfDay = Date.from(today.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

        return movieRepository.findAll().stream()
                .filter(movie -> movie.getIsShowing() == 1  // Phim đang chiếu
                        && movie.getReleaseDate() != null
                        && !movie.getReleaseDate().after(endOfDay))  // Phim có ngày phát hành từ hôm nay trở về trước
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Page<MovieDTO> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    // Lấy các phim sắp ra mắt
    @Override
    public List<MovieDTO> getUpcomingMovies() {
        Date currentDate = new Date();

        return movieRepository.findAll().stream()
                .filter(movie -> movie.getIsShowing() == 1  // Phim đang chiếu
                        && movie.getReleaseDate() != null
                        && movie.getReleaseDate().after(currentDate))  // Phim có ngày phát hành sau ngày hiện tại
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    // Lấy các phim sẽ ra mắt trong ngày
    @Override
    public List<MovieDTO> getMoviesToday() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        Date startOfDay = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return movieRepository.findAll().stream()
                .filter(movie -> movie.getReleaseDate() != null
                        && !movie.getReleaseDate().before(startOfDay)
                        && movie.getReleaseDate().before(endOfDay))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy phim theo ID
    @Override
    public MovieDTO getById(Integer movieId) {
        return movieRepository.findById(movieId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Movie with ID " + movieId + " not found"));
    }

//    @Override
//    public List<MovieDTO> getAllMoviesWithType() {
//        return movieRepository.getAllMoviesWithType();
//    }

    // Tạo phim mới
    @Override
    @Transactional
    public MovieDTO createMovie(MovieDTO movieDTO) {
        // Convert DTO to Entity
        MovieEntity movie = convertToEntity(movieDTO);

        // Save movie entity to database
        MovieEntity savedMovie = movieRepository.save(movie);

        // Convert back to DTO and return
        return convertToDTO(savedMovie);
    }

    // Cập nhật phim
    @Override
    public MovieDTO updateMovie(MovieDTO movieDTO) {
        MovieEntity movie = movieRepository.findById(movieDTO.getMovieId())
                .orElseThrow(() -> {
                    return new  EntityNotFoundException("Movie with ID " + movieDTO.getMovieId() + " not found");
                });

        // Cập nhật các trường của MovieEntity từ MovieDTO
        movie.setDescription(movieDTO.getDescription());
        movie.setProductionCompany(movieDTO.getProductionCompany());
        movie.setDirector(movieDTO.getDirector());
        movie.setTitle(movieDTO.getTitle());
        movie.setImage(movieDTO.getImage());
        movie.setTrailerUrl(movieDTO.getTrailerUrl());
        movie.setVersion(movieDTO.getVersion());
        movie.setActor(movieDTO.getActor());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setRunningTime(movieDTO.getRunningTime());
        movie.setIsShowing(movieDTO.getIsShowing());

        // Lưu và trả về MovieDTO sau khi cập nhật
        MovieEntity updatedMovie = movieRepository.save(movie);
        return convertToDTO(updatedMovie);
    }

    @Override
    public MovieEntity save(MovieEntity movie) {
        return movieRepository.save(movie);  // This will save or update the MovieEntity
    }
    @Override
    public Optional<MovieEntity> findById(Integer movieId) {
        return movieRepository.findById(movieId);  // Use the repository method to find a movie by ID
    }

    @Override
    public List<TypeEntity> getTypeMoVie() {
        return typeRepository.findAll();  // Trả về tất cả thể loại phim từ cơ sở dữ liệu
    }

    @Override
    public TypeEntity getTypeById(Integer typeId) {
        Optional<TypeEntity> typeEntity = typeRepository.findById(typeId);
        return typeEntity.orElseThrow(() -> new RuntimeException("Type not found with id: " + typeId));
    }

    @Override
    public void saveMovieType(MovieTypeEntity movieTypeEntity) {
        movieTypeRepository.save(movieTypeEntity);  // Giả sử bạn có repository cho MovieTypeEntity
    }



    // Xóa phim
    @Override
    public void deleteMovie(int movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new EntityNotFoundException("Movie with ID " + movieId + " not found");
        }
        showtimeRepository.deleteByMovieId(movieId);
        movieRepository.deleteById(movieId);
    }

    public String uploadImage(MultipartFile image) {
        try {
            Path path = Paths.get(UPLOAD_DIR + image.getOriginalFilename());
            Files.write(path, image.getBytes());
            System.out.println("Saving image to: " + path.toString());
            return path.toString();  // Trả về URL ảnh lưu trữ
        } catch (IOException e) {
            throw new RuntimeException("Error uploading image", e);
        }
    }

    // Phương thức chuyển đổi MovieEntity thành MovieDTO
    private MovieDTO convertToDTO(MovieEntity entity) {
        MovieDTO dto = new MovieDTO();
        dto.setMovieId(entity.getMovieId());
        dto.setDescription(entity.getDescription());
        dto.setProductionCompany(entity.getProductionCompany());
        dto.setDirector(entity.getDirector());
        dto.setTitle(entity.getTitle());
        dto.setImage(entity.getImage());
        dto.setTrailerUrl(entity.getTrailerUrl());
        dto.setVersion(entity.getVersion());
        dto.setActor(entity.getActor());
        dto.setReleaseDate(entity.getReleaseDate());
        dto.setRunningTime(entity.getRunningTime());
        dto.setIsShowing(entity.getIsShowing());
        return dto;
    }

    // Phương thức chuyển đổi MovieDTO thành MovieEntity
    private MovieEntity convertToEntity(MovieDTO dto) {
        MovieEntity entity = new MovieEntity();
        entity.setMovieId(dto.getMovieId());
        entity.setDescription(dto.getDescription());
        entity.setProductionCompany(dto.getProductionCompany());
        entity.setDirector(dto.getDirector());
        entity.setTitle(dto.getTitle());
        entity.setImage(dto.getImage());
        entity.setTrailerUrl(dto.getTrailerUrl());
        entity.setVersion(dto.getVersion());
        entity.setActor(dto.getActor());
        entity.setReleaseDate(dto.getReleaseDate());
        entity.setRunningTime(dto.getRunningTime());
        entity.setIsShowing(dto.getIsShowing());
        return entity;
    }
    @Override
    public List<MovieDTO> searchMoviesByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title); // Tìm kiếm phim có title chứa từ khóa (không phân biệt chữ hoa/thường)
    }

    @Override
    public Page<MovieDTO> getMoviesPage(int entriesPerPage, int page) {
        // Phân trang sử dụng PageRequest, page bắt đầu từ 0, vì vậy cần trừ 1 nếu page từ frontend bắt đầu từ 1
        Pageable pageable = PageRequest.of(page - 1, entriesPerPage);  // Giảm 1 vì PageRequest yêu cầu chỉ số trang bắt đầu từ 0

        // Lấy danh sách phim với phân trang từ repository
        Page<MovieEntity> moviePage = movieRepository.findAll(pageable);

        // Chuyển đổi từ Page<MovieEntity> sang Page<MovieDTO> bằng cách sử dụng .map()
        Page<MovieDTO> movieDTOPage = moviePage.map(this::convertToDTO);

        // Trả về Page<MovieDTO>
        return movieDTOPage;
    }

//    @Override
//    public List<MovieDTO> findAllShowingMoviesByName(String keyword) {
//        return movieRepository.findBy(MovieDTO);
//    }
}
