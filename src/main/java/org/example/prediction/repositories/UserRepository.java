package org.example.prediction.repositories;

import org.example.prediction.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Page<User> findAllByUsernameContainingIgnoreCase(String search, Pageable pageable);
}