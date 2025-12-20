package com.example.server.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "calendar_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Пара пользователей, у которых общее событие
    private Long userAId;
    private Long userBId;

    // Кто создал событие
    private Long authorId;

    private LocalDate date;

    @Column(columnDefinition = "TEXT")
    private String text;

    private String icon;

    private LocalDateTime createdAt = LocalDateTime.now();
}
