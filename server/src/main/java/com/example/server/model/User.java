package com.example.server.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long partnerId;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MoodType mood = MoodType.CALM; // дефолтное значение
}
