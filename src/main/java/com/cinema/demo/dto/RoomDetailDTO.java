package com.cinema.demo.dto;

import lombok.Data;

@Data
public class RoomDetailDTO {
    private int chair;
    private int movieProjector;
    private int loudspeaker;
    private int led;
    private String note;
    private Boolean status;
    private int idCinemaRoom; // ID của phòng chiếu
}
