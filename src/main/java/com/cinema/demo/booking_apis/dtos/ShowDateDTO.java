package com.cinema.demo.booking_apis.dtos;

import com.cinema.demo.entities.ShowtimeEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ShowDateDTO {
    private int showtimeDateId;

    private LocalDate startDate;

    private ShowtimeEntity showtime;
}
