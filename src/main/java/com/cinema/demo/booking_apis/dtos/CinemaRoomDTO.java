package com.cinema.demo.booking_apis.dtos;

import lombok.Data;

@Data
public class CinemaRoomDTO {

    private int id;

    private String cinemaRoomNum;

    private int capacity;

    private double totalArea;

    private String imgURL;
}
