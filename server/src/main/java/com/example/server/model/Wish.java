package com.example.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "wishes")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    @Column(nullable = false)
    private String title = "";

    @Column(nullable = false)
    private String description = "";

    private String link;

    @Column(nullable = false)
    private String categoryIcon = "★";

    @Column(nullable = false)
    private boolean isDone = false;

    @Column(nullable = false)
    private boolean isFavorite = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Wish() {
    }

    public Wish(Long userId, String title, String description, String link, Boolean isFavorite, String categoryIcon) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.link = link;
        this.isFavorite = isFavorite;
        this.categoryIcon = categoryIcon;
    }
}
