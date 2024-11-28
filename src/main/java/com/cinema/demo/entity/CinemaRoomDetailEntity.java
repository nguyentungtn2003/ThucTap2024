package com.cinema.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "cinema_room_detail")

@Data
@AllArgsConstructor
public class CinemaRoomDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "chair", nullable = false)
    private int chair;

    @Column(name = "movie_projector", nullable = false)
    private int movieProjector;

    @Column(name = "loudspeaker", nullable = false)
    private int loudspeaker;

    @Column(name = "led", nullable = false)
    private int led;

    @Column(name = "note", length = 355)
    private String note;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "id_cinema_room", nullable = false)
    @ToString.Exclude // Loại bỏ khỏi toString
    @JsonBackReference
    private CinemaRoomEntity room;

    public CinemaRoomDetailEntity() {}

    @PrePersist
    protected void onCreate() {
        this.date = LocalDateTime.now(); // Gán ngày hiện tại
    }

    // Gán giá trị ngày khi cập nhật
    @PreUpdate
    protected void onUpdate() {
        this.date = LocalDateTime.now();
    }
}
