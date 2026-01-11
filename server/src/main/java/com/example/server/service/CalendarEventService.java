package com.example.server.service;

import com.example.server.model.CalendarEvent;
import com.example.server.dto.EventItemDto;
import com.example.server.model.CreateUpdateEventRequest;
import com.example.server.repository.CalendarEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalendarEventService {

    private final CalendarEventRepository repo;

    public CalendarEventService(CalendarEventRepository repo) {
        this.repo = repo;
    }

    private long[] normalizePair(Long userId, Long partnerId) {
        if (userId <= partnerId) return new long[]{userId, partnerId};
        return new long[]{partnerId, userId};
    }

    public List<EventItemDto> getAll(Long userId, Long partnerId) {
        long[] pair = normalizePair(userId, partnerId);
        return repo.findAllByUserAIdAndUserBIdOrderByDateAsc(pair[0], pair[1])
                .stream()
                .map(EventItemDto::from)
                .collect(Collectors.toList());
    }

    public EventItemDto create(Long userId, Long partnerId, CreateUpdateEventRequest req) {
        long[] pair = normalizePair(userId, partnerId);

        CalendarEvent e = new CalendarEvent();
        e.setUserAId(pair[0]);
        e.setUserBId(pair[1]);
        e.setAuthorId(req.authorId);
        e.setDate(LocalDate.parse(req.date));
        e.setText(req.text);
        e.setIcon(req.icon != null ? req.icon : "🎀");

        repo.save(e);
        return EventItemDto.from(e);
    }

    public EventItemDto update(Long userId, Long partnerId, Long id, CreateUpdateEventRequest req) {
        long[] pair = normalizePair(userId, partnerId);

        CalendarEvent e = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!(e.getUserAId().equals(pair[0]) && e.getUserBId().equals(pair[1])))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        if (req.date != null) e.setDate(LocalDate.parse(req.date));
        if (req.text != null) e.setText(req.text);
        if (req.icon != null) e.setIcon(req.icon);
        if (req.authorId != null) e.setAuthorId(req.authorId);

        repo.save(e);
        return EventItemDto.from(e);
    }

    public void delete(Long userId, Long partnerId, Long id) {
        long[] pair = normalizePair(userId, partnerId);

        CalendarEvent e = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!(e.getUserAId().equals(pair[0]) && e.getUserBId().equals(pair[1])))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        repo.delete(e);
    }
}
