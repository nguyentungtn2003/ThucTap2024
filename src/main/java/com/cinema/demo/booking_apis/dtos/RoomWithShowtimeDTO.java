package com.cinema.demo.booking_apis.dtos;

import lombok.Data;

import java.util.List;

@Data
public class RoomWithShowtimeDTO {
    private String roomName;
    private List<String> showtime;
}
