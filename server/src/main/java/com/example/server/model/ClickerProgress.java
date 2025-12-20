package com.example.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "clicker_progress")
public class ClickerProgress {

    @Id
    private Long userId;

    private Integer clicks = 0;

    private Integer level = 1;

    private LocalDateTime lastUpdate = LocalDateTime.now();
}
