package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.dtos.CinemaRoomDTO;
import com.cinema.demo.booking_apis.repositories.ICinemaRoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService implements IRoomService{
    @Autowired
    private ICinemaRoomRepository roomRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CinemaRoomDTO> getRooms(Integer movieId, String startDate, String startTime) {
        return roomRepository.getCinemaRoomEntityByMovieAndShowDateAndShowTime(movieId,LocalDate.parse(startDate), LocalTime.parse(startTime))
                .stream().map(room -> modelMapper.map(room,CinemaRoomDTO.class))
                .collect(Collectors.toList());
    }
}
