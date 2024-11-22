package com.cinema.demo.booking_apis.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class MovieDTO {
    private int movieId;

    private String smallImageURl;

    private String largeImageURL;

    private String shortDescription;

    private String longDescription;

    private String trailerURL;

    private String productionCompany;

    private String director;

    private String title;

    private String version;

    private String actor;

    private Date releaseDate;

    private int runningTime;

    private String language;

    private String rated;

    private int isShowing;

    private List<String> types;

}
