package org.example.bet.repositories;

import org.example.bet.models.entities.EventOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventOptionRepository extends JpaRepository<EventOption, Long> {
}
