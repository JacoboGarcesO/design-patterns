package com.example.patterns_banking.controllers;

import com.example.patterns_banking.dtos.CardDto;
import com.example.patterns_banking.models.Card;
import com.example.patterns_banking.services.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService){
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody CardDto cardDto) {
        return ResponseEntity.ok(cardService.create(cardDto));
    }
}
