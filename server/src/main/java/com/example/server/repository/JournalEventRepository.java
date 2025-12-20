package com.example.server.repository;

import com.example.server.model.DiaryEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalEventRepository extends JpaRepository<DiaryEvent, Long> {
    List<DiaryEvent> findAllByRelationIdOrderByCreatedAtDesc(Long relationId);
}
