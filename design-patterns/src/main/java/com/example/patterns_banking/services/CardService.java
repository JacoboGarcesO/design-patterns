package com.example.patterns_banking.services;

import com.example.patterns_banking.dtos.CardDto;
import com.example.patterns_banking.models.Card;
import com.example.patterns_banking.repositories.CardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private CardRepository cardRepository;

    public CardService(){
        this.cardRepository = CardRepository.getInstance();
    }

    public Card create(CardDto cardDto){
        Card card = Card.builder()
                .number(cardDto.getNumber())
                .accountType(cardDto.getType())
                .balance(cardDto.getBalance())
                .isActive(cardDto.getIsActive())
                .build();

        return cardRepository.save(card);
    }
}
