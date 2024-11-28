package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.dtos.*;
import com.cinema.demo.booking_apis.mappers.SeatTypeMapper;
import com.cinema.demo.booking_apis.repository.IBookingRepository;
import com.cinema.demo.booking_apis.repository.ISeatBookingRepository;
import com.cinema.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatBookingService implements ISeatBookingService {
    @Autowired
    private ISeatBookingRepository seatBookingRepository;

    @Autowired
    private IBookingRepository bookingRepository;

    @Override
    public void saveSeatBooking(SeatBookingDTO seatBookingDTO) {
        SeatBookingEntity seatBookingEntity = new SeatBookingEntity();
        // Chuyển đổi SeatBookingDTO thành SeatBookingEntity và lưu vào DB
        seatBookingRepository.save(seatBookingEntity);
    }

    @Override
    public List<SeatBookingDTO> getSeatsByBookingId(int bookingId) {
        BookingEntity bookingEntity = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        BookingDTO bookingDTO = convertToBookingDTO(bookingEntity);

        List<SeatBookingEntity> seatBookings = seatBookingRepository.findByBooking_BookingId(bookingEntity.getBookingId());

        return seatBookings.stream().map(seatBooking -> {
            SeatBookingDTO seatBookingDTO = new SeatBookingDTO();
            seatBookingDTO.setId(seatBooking.getId());

            SeatDTO seatDTO = convertToSeatDTO(seatBooking.getSeat());
            seatBookingDTO.setSeat(seatDTO);

            seatBookingDTO.setBooking(bookingDTO);

            return seatBookingDTO;
        }).collect(Collectors.toList());
    }

    private BookingDTO convertToBookingDTO(BookingEntity bookingEntity) {
        if (bookingEntity == null) {
            return null;
        }

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(bookingEntity.getBookingId());
        bookingDTO.setTotalFoodTicketAmount(bookingEntity.getTotalFoodTicketAmount());
        bookingDTO.setBookingFee(bookingEntity.getBookingFee());
        bookingDTO.setStatus(bookingEntity.getStatus());
        bookingDTO.setCreatedDateTime(bookingEntity.getCreatedDateTime());

        return bookingDTO;
    }

    private SeatDTO convertToSeatDTO(SeatEntity seatEntity) {
        if (seatEntity == null) {
            return null;
        }

        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setSeatId(seatEntity.getSeatId());
        seatDTO.setSeatPosition(seatEntity.getSeatPosition());

        CinemaRoomDTO cinemaRoomDTO = convertToCinemaRoomDTO(seatEntity.getCinemaRoom());
        seatDTO.setCinemaRoom(cinemaRoomDTO);

        SeatTypeDTO seatTypeDTO = convertToSeatTypeDTO(seatEntity.getSeatType());
        seatDTO.setSeatType(seatTypeDTO);

        ShowtimeDTO showtimeDTO = convertToShowtimeDTO(seatEntity.getShowtime());
        seatDTO.setShowtime(showtimeDTO);

        seatDTO.setIsOccupied(seatEntity.getIsOccupied());

        return seatDTO;
    }

    private ShowtimeDTO convertToShowtimeDTO(ShowtimeEntity showtimeEntity) {
        if (showtimeEntity == null) {
            return null;
        }
        ShowtimeDTO showtimeDTO = new ShowtimeDTO();
        showtimeDTO.setShowtimeId(showtimeEntity.getShowtimeId());
        showtimeDTO.setStartTime(showtimeEntity.getStartTime());

        CinemaRoomDTO cinemaRoomDTO = convertToCinemaRoomDTO(showtimeEntity.getCinemaRoom());
        showtimeDTO.setCinemaRoom(cinemaRoomDTO);

        MovieDTO movieDTO = convertToMovieDTO(showtimeEntity.getMovie());
        showtimeDTO.setMovie(movieDTO);

        return showtimeDTO;
    }

    private MovieDTO convertToMovieDTO(MovieEntity movieEntity) {
        if (movieEntity == null) {
            return null;
        }
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setMovieId(movieEntity.getMovieId());
        movieDTO.setTitle(movieEntity.getTitle());
        return movieDTO;
    }

    private CinemaRoomDTO convertToCinemaRoomDTO(CinemaRoomEntity cinemaRoomEntity) {
        if (cinemaRoomEntity == null) {
            return null;
        }
        CinemaRoomDTO cinemaRoomDTO = new CinemaRoomDTO();
        cinemaRoomDTO.setId(cinemaRoomEntity.getId());
        cinemaRoomDTO.setCinemaRoomNum(cinemaRoomEntity.getCinemaRoomNum());
        return cinemaRoomDTO;
    }

    private SeatTypeDTO convertToSeatTypeDTO(SeatTypeEntity seatTypeEntity) {
        return SeatTypeMapper.toDTO(seatTypeEntity);
    }

}
