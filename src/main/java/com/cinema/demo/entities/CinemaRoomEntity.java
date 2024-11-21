package com.cinema.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CinemaRoom")
public class CinemaRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String cinemaRoomNum;

    private int capacity;

    private double totalArea;

    @Column(length = 1000)
    private String imgURL;
}
