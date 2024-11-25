package com.cinema.demo.dto;

import com.cinema.demo.entity.TypeEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class MovieDTO {

    private int movieId;

    @NotEmpty(message = "Title is required")
    @Size(min = 1, max = 100, message = "Title length should be between 1 and 100 characters")
    private String title;

    @NotEmpty(message = "Description is required")
    private String description;

  //  @NotEmpty(message = "Production Company is required")
    private String productionCompany;

   @NotEmpty(message = "Director is required")
    private String director;

    private String image;

    private String trailerUrl;

    private String version;

    private String actor;

 //   @NotNull(message = "Release Date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Định dạng ngày theo yyyy-MM-dd
    private Date releaseDate;

    @NotNull(message = "Running Time is required")
   @Positive(message = "Running Time must be positive")
    private int runningTime;

    private int isShowing;  // 1 = Đang chiếu, 0 = Không chiếu

    public MovieDTO() {
    }

    public MovieDTO(int movieId, String title, String description, String productionCompany,
                    String director, String image, String trailerUrl, String version, String actor,
                    Date releaseDate, int runningTime, int isShowing) {
        this.movieId = movieId;
        this.title = title;
        this.description = description;
        this.productionCompany = productionCompany;
        this.director = director;
        this.image = image;
        this.trailerUrl = trailerUrl;
        this.version = version;
        this.actor = actor;
        this.releaseDate = releaseDate;
        this.runningTime = runningTime;
        this.isShowing = isShowing;
    }


    private List<Integer> typeIds;

    private List<TypeEntity> types;  // Thêm thuộc tính này để chứa danh sách các đối tượng TypeEntity
}
