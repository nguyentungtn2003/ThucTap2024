package com.cinema.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @ToString.Exclude // Loại bỏ khỏi toString
    @JsonManagedReference
    private List<CinemaRoomDetailEntity> roomDetails;
}
