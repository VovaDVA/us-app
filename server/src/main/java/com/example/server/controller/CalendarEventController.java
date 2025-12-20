package com.example.server.controller;

import com.example.server.model.CalendarEvent;
import com.example.server.model.EventItemDto;
import com.example.server.model.CreateUpdateEventRequest;
import com.example.server.repository.CalendarEventRepository;
import com.example.server.service.CalendarEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class CalendarEventController {

    private final CalendarEventService service;
    private final CalendarEventRepository calendarEventRepository;

    public CalendarEventController(CalendarEventService service, CalendarEventRepository calendarEventRepository) {
        this.service = service;
        this.calendarEventRepository = calendarEventRepository;
    }

    @GetMapping
    public List<EventItemDto> getAll(
            @RequestParam Long userId,
            @RequestParam Long partnerId
    ) {
        return service.getAll(userId, partnerId);
    }

    @GetMapping("/next")
    public ResponseEntity<EventItemDto> getNextEvent(
            @RequestParam Long userId,
            @RequestParam Long partnerId
    ) {
        LocalDate today = LocalDate.now();
        return calendarEventRepository
                .findFirstByDateGreaterThanEqualAndUserAIdInAndUserBIdInOrderByDateAsc(
                        today,
                        List.of(userId, partnerId),
                        List.of(userId, partnerId)
                )
                .map(EventItemDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }


    @PostMapping
    public EventItemDto create(
            @RequestParam Long userId,
            @RequestParam Long partnerId,
            @RequestBody CreateUpdateEventRequest req
    ) {
        return service.create(userId, partnerId, req);
    }

    @PutMapping("/{id}")
    public EventItemDto update(
            @RequestParam Long userId,
            @RequestParam Long partnerId,
            @PathVariable Long id,
            @RequestBody CreateUpdateEventRequest req
    ) {
        return service.update(userId, partnerId, id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @RequestParam Long userId,
            @RequestParam Long partnerId,
            @PathVariable Long id
    ) {
        service.delete(userId, partnerId, id);
    }
}
