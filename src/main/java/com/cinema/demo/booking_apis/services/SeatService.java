package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.dtos.SeatDTO;
import com.cinema.demo.booking_apis.repository.ISeatRepository;
import com.cinema.demo.booking_apis.repository.ISeatTypeRepository;
import com.cinema.demo.booking_apis.repository.IShowTimeRepository;
import com.cinema.demo.booking_apis.repository.ITicketRepository;
import com.cinema.demo.entity.CinemaRoomEntity;
import com.cinema.demo.entity.SeatEntity;
import com.cinema.demo.entity.SeatTypeEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService implements ISeatService {
    @Autowired
    private ISeatRepository seatRepository;

    @Autowired
    private ISeatTypeRepository seatTypeRepository;

    @Autowired
    private IShowTimeRepository showTimeRepository;
    @Autowired
    private ITicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SeatDTO> getSeatsByShowTimeId(Integer showtimeId) {
        CinemaRoomEntity room = showTimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch chiếu với ID: " + showtimeId))
                .getCinemaRoom();

        List<SeatEntity> listSeat = seatRepository.getSeatEntityByCinemaRoom_Id(room.getId());

        List<SeatEntity> occupiedSeats = ticketRepository.findTicketEntityByShowtime_ShowtimeId(showtimeId)
                .stream().map(ticket -> ticket.getSeat())
                .collect(Collectors.toList());

        List<SeatDTO> filteredSeats = listSeat.stream().map(seat -> {
            SeatDTO seatDTO = modelMapper.map(seat,SeatDTO.class);
            if(occupiedSeats.stream()
                    .map(occupiedSeat->occupiedSeat.getSeatId())
                    .collect(Collectors.toList()).contains(seat.getSeatId())){
                seatDTO.setIsOccupied(2);
            }
            return seatDTO;
        }).collect(Collectors.toList());

        return  filteredSeats;
    }

    @Override
    public SeatDTO getSeatById(Integer seatId) {
        SeatEntity seatEntity = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ghế với ID: " + seatId));

        return modelMapper.map(seatEntity, SeatDTO.class);
    }

    @Override
    public void updateSeatStatus(SeatDTO seatDTO) {
        SeatEntity seatEntity = seatRepository.findById(seatDTO.getSeatId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ghế với ID: " + seatDTO.getSeatId()));

        seatEntity.setIsOccupied(seatDTO.getIsOccupied());
        seatRepository.save(seatEntity);
    }

    @Override
    public BigDecimal getPriceBySeatType(String seatName) {
        SeatTypeEntity seatTypeEntity = seatTypeRepository.findBySeatName(seatName)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại ghế với tên: " + seatName));

        return seatTypeEntity.getSeatPrice();
    }

    @Override
    public SeatEntity getSeatEntityById(Integer seatId) {
        return seatRepository.findById(seatId)
                .orElse(null);
    }
}
