package com.cinema.demo.booking_apis.dtos;

import lombok.Data;

@Data
public class MovieTypeDTO {
    private int id;
    private MovieDTO movie;
    private TypeDTO type;
}
