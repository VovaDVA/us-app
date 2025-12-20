package com.example.server.controller;

import com.example.server.model.DiaryEvent;
import com.example.server.repository.JournalEventRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journal")
public class JournalController {

    private final JournalEventRepository repo;

    public JournalController(JournalEventRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{relationId}")
    public List<DiaryEvent> getAll(@PathVariable Long relationId) {
        return repo.findAllByRelationIdOrderByCreatedAtDesc(relationId);
    }

    @PostMapping
    public DiaryEvent create(@RequestBody DiaryEvent event) {
        return repo.save(event);
    }
}
