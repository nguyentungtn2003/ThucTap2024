package com.cinema.demo.dto;

import lombok.Data;
import java.util.Date;

@Data
public class MovieDTO {
    private int movieId;
    private String description;
    private String productionCompany;
    private String director;
    private String title;
    private String image;
    private String trailerUrl;
    private String version;
    private String actor;
    private Date releaseDate;
    private int runningTime;
}
