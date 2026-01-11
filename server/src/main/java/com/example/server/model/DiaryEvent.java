package com.example.server.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "diary_events")
public class DiaryEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long partnerId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageSmallUrl;
    private String imageLargeUrl;

    private Long date; // epoch millis

    @Enumerated(EnumType.STRING)
    private EventType type;
}
