package com.example.server.controller;

import com.example.server.dto.DiaryEventDto;
import com.example.server.model.DiaryEvent;
import com.example.server.model.User;
import com.example.server.repository.DiaryEventRepository;
import com.example.server.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryEventRepository repository;
    private final AuthService authService;

    // 📖 Получить все события дневника
    @GetMapping
    public List<DiaryEventDto> getAll(
            @RequestHeader("Authorization") String auth
    ) {
        User user = authService.requireUser(auth);

        return repository
                .findAllEvents(
                        user.getId()
                )
                .stream()
                .map(DiaryEventDto::from)
                .toList();
    }

    // ➕ Создать событие
    @PostMapping
    public DiaryEventDto create(
            @RequestHeader("Authorization") String auth,
            @RequestBody DiaryEventDto dto
    ) {
        User user = authService.requireUser(auth);

        DiaryEvent e = new DiaryEvent();
        e.setUserId(user.getId());
        e.setPartnerId(user.getPartnerId());
        e.setTitle(dto.title);
        e.setDescription(dto.description);
        e.setImageSmallUrl(dto.imageSmallUrl);
        e.setImageLargeUrl(dto.imageLargeUrl);
        e.setDate(dto.date);
        e.setType(dto.type);

        return DiaryEventDto.from(repository.save(e));
    }

    // ✏️ Обновить событие
    @PutMapping("/{id}")
    public DiaryEventDto update(
            @RequestHeader("Authorization") String auth,
            @PathVariable Long id,
            @RequestBody DiaryEventDto dto
    ) {
        User user = authService.requireUser(auth);

        DiaryEvent e = repository.findById(id)
                .orElseThrow();

        // 🔐 защита от чужих записей
        if (!e.getUserId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        e.setTitle(dto.title);
        e.setDescription(dto.description);
        e.setImageSmallUrl(dto.imageSmallUrl);
        e.setImageLargeUrl(dto.imageLargeUrl);
        e.setDate(dto.date);
        e.setType(dto.type);

        return DiaryEventDto.from(repository.save(e));
    }

    // 🗑 Удалить событие
    @DeleteMapping("/{id}")
    public void delete(
            @RequestHeader("Authorization") String auth,
            @PathVariable Long id
    ) {
        User user = authService.requireUser(auth);

        DiaryEvent e = repository.findById(id)
                .orElseThrow();

        // 🔐 защита
        if (!e.getUserId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        repository.delete(e);
    }
}
