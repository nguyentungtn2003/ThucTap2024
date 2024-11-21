package com.cinema.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "ShowDate")
public class ShowDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int showtimeDateId;

    private LocalDate startDate;

    @ManyToOne
    @JoinColumn(name = "showtimeId")
    @JsonBackReference
    private ShowtimeEntity showtime;
}
