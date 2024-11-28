package com.cinema.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "ShowDate")
public class ShowDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int showtimeDateId;

    private LocalDate startDate;

//    @ManyToOne
//    @JoinColumn(name = "showtimeId")
//    private ShowtimeEntity showtime;

    @OneToMany(mappedBy = "showDate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowtimeEntity> showtime;
}
