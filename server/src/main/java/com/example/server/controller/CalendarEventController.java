package com.example.server.controller;

import com.example.server.dto.EventItemDto;
import com.example.server.model.CreateUpdateEventRequest;
import com.example.server.model.User;
import com.example.server.repository.CalendarEventRepository;
import com.example.server.service.AuthService;
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
    private final AuthService authService;

    public CalendarEventController(
            CalendarEventService service,
            CalendarEventRepository calendarEventRepository,
            AuthService authService
    ) {
        this.service = service;
        this.calendarEventRepository = calendarEventRepository;
        this.authService = authService;
    }

    // 📅 Все события
    @GetMapping
    public List<EventItemDto> getAll(
            @RequestHeader("Authorization") String auth
    ) {
        User user = authService.requireUser(auth);
        return service.getAll(user.getId(), user.getPartnerId());
    }

    // ⏭ Ближайшее событие
    @GetMapping("/next")
    public ResponseEntity<EventItemDto> getNextEvent(
            @RequestHeader("Authorization") String auth
    ) {
        User user = authService.requireUser(auth);
        LocalDate today = LocalDate.now();

        return calendarEventRepository
                .findFirstByDateGreaterThanEqualAndUserAIdInAndUserBIdInOrderByDateAsc(
                        today,
                        List.of(user.getId(), user.getPartnerId()),
                        List.of(user.getId(), user.getPartnerId())
                )
                .map(EventItemDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // ➕ Создать событие
    @PostMapping
    public EventItemDto create(
            @RequestHeader("Authorization") String auth,
            @RequestBody CreateUpdateEventRequest req
    ) {
        User user = authService.requireUser(auth);
        return service.create(user.getId(), user.getPartnerId(), req);
    }

    // ✏️ Обновить событие
    @PutMapping("/{id}")
    public EventItemDto update(
            @RequestHeader("Authorization") String auth,
            @PathVariable Long id,
            @RequestBody CreateUpdateEventRequest req
    ) {
        User user = authService.requireUser(auth);
        return service.update(user.getId(), user.getPartnerId(), id, req);
    }

    // 🗑 Удалить событие
    @DeleteMapping("/{id}")
    public void delete(
            @RequestHeader("Authorization") String auth,
            @PathVariable Long id
    ) {
        User user = authService.requireUser(auth);
        service.delete(user.getId(), user.getPartnerId(), id);
    }
}
