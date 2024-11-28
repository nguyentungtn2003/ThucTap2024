package com.cinema.demo.service;

import com.cinema.demo.dto.RoomDetailsDTO;
import com.cinema.demo.entity.CinemaRoomDetailEntity;
import com.cinema.demo.entity.ShowtimeEntity;
import com.cinema.demo.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerRoomService {
    @Autowired
    private ShowtimeRepository showtimeRepository;

    public List<RoomDetailsDTO> getAllInfoRoom(){
        List<RoomDetailsDTO> roomInfo = new ArrayList<>();
        List<ShowtimeEntity> showtimeEntityList = showtimeRepository.findAll();
        for (ShowtimeEntity showtimeEntity : showtimeEntityList){
            // Lấy danh sách RoomDetails
            List<CinemaRoomDetailEntity> roomDetails = showtimeEntity.getCinemaRoom().getRoomDetails();
            if (roomDetails != null ) {
                CinemaRoomDetailEntity  roomDetailLast = roomDetails.get(roomDetails.size()-1);
                roomInfo.add(new RoomDetailsDTO(
                        showtimeEntity.getCinemaRoom().getId(),
                        showtimeEntity.getCinemaRoom().getCinemaRoomNum(),
                        roomDetailLast.getChair(),
                        showtimeEntity.getMovie().getMovieName(),
                        showtimeEntity.getMovie().getRunningTime(),
                        showtimeEntity.getMovie().getIsShowing(),
                        showtimeEntity.getMovie().getReleaseDate(),
                        roomDetailLast
                ));
            }
        }
        return roomInfo;
    }
}
