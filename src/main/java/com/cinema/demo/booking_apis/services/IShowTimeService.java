package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.dtos.ShowtimeDTO;

import java.time.LocalDate;
import java.util.List;

public interface IShowTimeService {
    List<String> getStartTimes(Integer movieId, LocalDate startDate);
    List<ShowtimeDTO> getSchedules(Integer movieId, String startDate, String startTime, Integer roomId);
}
