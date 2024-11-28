package com.cinema.demo.booking_apis.dtos;

import com.cinema.demo.entity.CinemaRoomEntity;
import com.cinema.demo.entity.SeatTypeEntity;
import com.cinema.demo.entity.ShowtimeEntity;
import lombok.Data;

@Data
public class SeatDTO {
    private int seatId;

    private String seatPosition;

    private CinemaRoomDTO cinemaRoom;

    private SeatTypeDTO seatType;

    private ShowtimeDTO showtime;

    private int isOccupied;
}
