package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.dtos.SeatDTO;
import com.cinema.demo.booking_apis.repository.ISeatRepository;
import com.cinema.demo.booking_apis.repository.IShowTimeRepository;
import com.cinema.demo.booking_apis.repository.ITicketRepository;
import com.cinema.demo.entity.CinemaRoomEntity;
import com.cinema.demo.entity.SeatEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService implements ISeatService {
    @Autowired
    private ISeatRepository seatRepository;
    @Autowired
    private IShowTimeRepository showTimeRepository;
    @Autowired
    private ITicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SeatDTO> getSeatsByShowTimeId(Integer showtimeId) {
        // Lấy phòng chiếu dựa trên lịch chiếu
        CinemaRoomEntity room = showTimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch chiếu với ID: " + showtimeId))
                .getCinemaRoom();

        // Lấy danh sách ghế trong phòng chiếu
        List<SeatEntity> listSeat = seatRepository.getSeatEntityByCinemaRoom_Id(room.getId());

        // Lấy ra các vé đã được đặt trong lịch đó rồi map sang các chỗ ngồi
        List<SeatEntity> occupiedSeats = ticketRepository.findTicketEntityByShowtime_ShowtimeId(showtimeId)
                .stream().map(ticket -> ticket.getSeat())
                .collect(Collectors.toList());

        // Map list chỗ ngồi của phòng ở bước 1 sang list dto
        List<SeatDTO> filteredSeats = listSeat.stream().map(seat -> {
            SeatDTO seatDTO = modelMapper.map(seat,SeatDTO.class);
            if(occupiedSeats.stream()
                    .map(occupiedSeat->occupiedSeat.getSeatId())
                    .collect(Collectors.toList()).contains(seat.getSeatId())){
                seatDTO.setIsOccupied(1); // Nếu ghế nào nằm trong list ghế đã được occupied thì set = 1
            }
            return seatDTO;
        }).collect(Collectors.toList());

        return  filteredSeats;
    }
}
