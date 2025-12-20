package com.example.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "diary_events")
public class DiaryEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long relationId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate date;

    private Boolean isImportant = false;

    private LocalDateTime createdAt = LocalDateTime.now();
}
