package com.example.server.repository;

import com.example.server.model.DiaryEvent;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaryEventRepository extends JpaRepository<DiaryEvent, Long> {

    List<DiaryEvent> findByUserIdAndPartnerIdOrderByDateDesc(
        Long userId,
        Long partnerId
    );


    @Query("SELECT event FROM DiaryEvent event WHERE event.userId = :userId OR event.partnerId = :userId")
    List<DiaryEvent> findAllEvents(@Param("userId") Long userId);
}
