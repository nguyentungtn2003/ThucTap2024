package com.cinema.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "Movie")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;
    private String movieName;
    private String description;

    private String productionCompany;

    private String director;

    private String title;

    private String image;

    private int is_showing;  // 1 = Đang chiếu, 0 = Không chiếu

    private String trailerurl;

    private String version;

    private String actor;

    private Date releaseDate;

    private int runningTime;

    private String language;

    private String rated;


    public String getTrailerUrl() {
        return trailerurl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerurl = trailerUrl;
    }

    public int getIsShowing() {
        return is_showing;
    }

    public void setIsShowing(int is_showing) {
        this.is_showing = is_showing;
    }


}
