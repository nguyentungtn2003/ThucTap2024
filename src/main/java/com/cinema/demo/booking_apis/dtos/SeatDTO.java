package com.cinema.demo.booking_apis.dtos;

import com.cinema.demo.entities.CinemaRoomEntity;
import com.cinema.demo.entities.SeatTypeEntity;
import com.cinema.demo.entities.ShowtimeEntity;
import lombok.Data;

@Data
public class SeatDTO {
    private int seatId;

    private String seatPosition;

    private CinemaRoomEntity cinemaRoom;

    private SeatTypeEntity seatType;

    private ShowtimeEntity showtime;

    private int isOccupied;
}
