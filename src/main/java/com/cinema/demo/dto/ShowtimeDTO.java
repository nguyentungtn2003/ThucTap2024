package com.cinema.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class ShowtimeDTO {
    private int movieId;
    private int cinemaRoomId;
    private List<String> showDates;  // Danh sách các ngày chiếu

}