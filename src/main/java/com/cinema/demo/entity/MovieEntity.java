package com.cinema.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "Movie")
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

    private int is_showing;

    private String trailerurl;

    private String version;

    private String actor;

    private Date releaseDate;

    private int runningTime;
}
