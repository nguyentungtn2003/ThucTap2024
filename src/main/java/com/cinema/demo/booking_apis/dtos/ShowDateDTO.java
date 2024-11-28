package com.cinema.demo.booking_apis.dtos;

import com.cinema.demo.entity.ShowtimeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ShowDateDTO {
    private int showtimeDateId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    private ShowtimeDTO showtime;
}
