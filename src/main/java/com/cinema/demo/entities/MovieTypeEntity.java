package com.cinema.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Movie_Type")
public class MovieTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "movieId")
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "typeId")
    private TypeEntity type;
}
