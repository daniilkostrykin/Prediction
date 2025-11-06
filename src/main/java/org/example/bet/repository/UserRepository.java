// package: org.example.bet.repository
package org.example.bet.repository;

import org.example.bet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}