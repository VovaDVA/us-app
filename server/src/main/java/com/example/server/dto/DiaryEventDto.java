package com.example.server.dto;

import com.example.server.model.DiaryEvent;
import com.example.server.model.EventType;

public class DiaryEventDto {
    public Long id;
    public String title;
    public String description;
    public Long date;
    public String imageSmallUrl; // вместо imageUrl
    public String imageLargeUrl; // вместо imageUrl
    public EventType type;

    public static DiaryEventDto from(DiaryEvent e) {
        DiaryEventDto d = new DiaryEventDto();
        d.id = e.getId();
        d.title = e.getTitle();
        d.description = e.getDescription();
        d.date = e.getDate();
        d.imageSmallUrl = e.getImageSmallUrl();
        d.imageLargeUrl = e.getImageLargeUrl();
        d.type = e.getType();
        return d;
    }
}