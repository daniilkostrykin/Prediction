// package: org.example.bet.repository
package org.example.bet.repository;

import org.example.bet.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}