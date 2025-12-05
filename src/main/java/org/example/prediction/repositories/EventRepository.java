package org.example.prediction.repositories;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.prediction.models.entities.Event;
import org.example.prediction.models.enums.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.options WHERE e.id = :id")
    Optional<Event> findByIdWithOptions(@Param("id") Long id);

    List<Event> findAllByStatusAndClosesAtBefore(EventStatus status, Instant now);
}