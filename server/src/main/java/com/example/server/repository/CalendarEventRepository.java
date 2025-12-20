package com.example.server.repository;

import com.example.server.model.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    List<CalendarEvent> findAllByUserAIdAndUserBIdOrderByDateAsc(Long userAId, Long userBId);

    Optional<CalendarEvent> findFirstByDateGreaterThanEqualAndUserAIdInAndUserBIdInOrderByDateAsc(
            LocalDate date,
            List<Long> userAIds,
            List<Long> userBIds
    );
}
