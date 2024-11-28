package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.dtos.ShowtimeDTO;
import com.cinema.demo.booking_apis.repository.IShowTimeRepository;
import com.cinema.demo.entity.ShowtimeEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowTimeService implements IShowTimeService {
    @Autowired
    private IShowTimeRepository showTimeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<String> getStartTimes(Integer movieId, LocalDate startDate) {
        return showTimeRepository.getStartTimeByMovieIdAndStartDate(movieId,startDate)
                .stream().map(localTime -> localTime.format(DateTimeFormatter.ofPattern("HH:mm")))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowtimeDTO> getSchedules(Integer movieId, String startDate, String startTime, Integer roomId) {
        return showTimeRepository.getSchedulesByMovieIdAndStartDateAndStartTimeAndRoomId(movieId, LocalDate.parse(startDate),LocalTime.parse(startTime), roomId)
                .stream().map(schedule -> modelMapper.map(schedule,ShowtimeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalDate> getAvailableDates(Integer movieId) {
        return showTimeRepository.findAvailableDatesByMovieId(movieId);  // Gọi phương thức repository mới tạo
    }

    @Override
    public ShowtimeDTO getShowtimeById(Integer showtimeId) {
        // Lấy suất chiếu từ cơ sở dữ liệu
        ShowtimeEntity showtimeEntity = showTimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Suất chiếu không tìm thấy với ID: " + showtimeId));  // Ném ngoại lệ nếu không tìm thấy

        // Chuyển đổi từ ShowtimeEntity sang ShowtimeDTO
        return modelMapper.map(showtimeEntity, ShowtimeDTO.class);
    }
    @Override
    public ShowtimeEntity getShowtimeEntityById(Integer showtimeId) {
        return showTimeRepository.findById(showtimeId)
                .orElse(null);
    }
}
