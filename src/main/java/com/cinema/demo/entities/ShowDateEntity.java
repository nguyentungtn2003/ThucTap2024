package com.cinema.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

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
    private ShowtimeEntity showtime;
}
