package com.cinema.demo.booking_apis.services;



import com.cinema.demo.booking_apis.dtos.CinemaRoomDTO;

import java.util.List;

public interface IRoomService {
    List<CinemaRoomDTO> getRooms(Integer movieId, String startDate, String startTime);
    CinemaRoomDTO getById(Integer roomId);
}
