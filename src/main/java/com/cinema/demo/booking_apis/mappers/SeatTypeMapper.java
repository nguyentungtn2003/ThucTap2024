package com.cinema.demo.booking_apis.mappers;

import com.cinema.demo.booking_apis.dtos.SeatTypeDTO;
import com.cinema.demo.entity.SeatTypeEntity;

public class SeatTypeMapper {

    public static SeatTypeDTO toDTO(SeatTypeEntity entity) {
        if (entity == null) {
            return null;
        }

        SeatTypeDTO dto = new SeatTypeDTO();
        dto.setSeatTypeId(entity.getSeatTypeId());
        dto.setSeatName(entity.getSeatName());
        return dto;
    }
}