package com.cinema.demo.service;

import com.cinema.demo.entity.CinemaRoomDetailEntity;
import com.cinema.demo.entity.CinemaRoomEntity;
import com.cinema.demo.repository.CinemaRoomDetailRepository;
import com.cinema.demo.repository.CinemaRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CinemaRoomDetailService {
    @Autowired
    private CinemaRoomDetailRepository cinemaRoomDetailRepository;

    @Autowired
    private CinemaRoomRepository cinemaRoomRepository;

    public List<CinemaRoomDetailEntity> getCinemaRoomDetailById(int idRoom) {
        CinemaRoomEntity cinemaRoom = cinemaRoomRepository.findCinemaRoomEntityById(idRoom);

        List<CinemaRoomDetailEntity> listCinemaRepository = cinemaRoomDetailRepository.findAllByRoom(cinemaRoom);

        List<CinemaRoomDetailEntity> listCinemaDetail = new ArrayList<>(listCinemaRepository);
        return listCinemaDetail;
    }

    public CinemaRoomEntity getCinemaRoom(int idRoom){
        CinemaRoomEntity cinemaRoom = cinemaRoomRepository.findCinemaRoomEntityById(idRoom);
        return cinemaRoom;
    }

    public CinemaRoomDetailEntity saveRoomDetail(CinemaRoomDetailEntity roomDetail) {
        return cinemaRoomDetailRepository.save(roomDetail);
    }

    public Optional<CinemaRoomDetailEntity> findRoomDetailById(int id){
        return cinemaRoomDetailRepository.findById(id);
    }

    public void deleteById(int id){
        cinemaRoomDetailRepository.deleteById(id);
    }
}
