package com.cinema.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "Movie")
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;

    @Column(length = 1000)
    private String smallImageURl;

    @Column(length = 1000)
    private String largeImageURL;

    @Column(length = 500)
    private String shortDescription;

    @Column(length = 1000)
    private String longDescription;

    @Column(length = 1000)
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

}
