package com.example.patterns_banking.repositories;

import com.example.patterns_banking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICardRepository extends JpaRepository<Card, Long> {
}
