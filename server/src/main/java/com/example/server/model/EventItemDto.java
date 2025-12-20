package com.example.server.model;

public class EventItemDto {
    public Long id;
    public String date;  // yyyy-MM-dd
    public String text;
    public String icon;
    public Long authorId;

    public static EventItemDto from(CalendarEvent e) {
        EventItemDto d = new EventItemDto();
        d.id = e.getId();
        d.date = e.getDate().toString();
        d.text = e.getText();
        d.icon = e.getIcon();
        d.authorId = e.getAuthorId();
        return d;
    }
}
