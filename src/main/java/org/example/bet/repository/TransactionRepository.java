// package: org.example.bet.repository
package org.example.bet.repository;

import org.example.bet.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}