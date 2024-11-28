package com.cinema.demo.dto;

import com.cinema.demo.entity.CinemaRoomDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class RoomDetailsDTO {
    private int id;
    private String nameRoom;
    private int chair;
    private String nameMovie;
    private int runingTime;
    private int status;
    private Date date;
    private CinemaRoomDetailEntity roomDetail;
}
