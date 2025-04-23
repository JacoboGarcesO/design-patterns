package com.example.patterns_banking.services;

import com.example.patterns_banking.dtos.AccountDTO;
import com.example.patterns_banking.models.Account;
import com.example.patterns_banking.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService() {
        this.repository = AccountRepository.getInstance();
    }

    public Account create(AccountDTO dto) {
        Account account = Account.builder()
                .number(dto.getNumber())
                .type(dto.getType())
                .balance(dto.getBalance())
                .isActive(dto.getIsActive())
                .build();

        return repository.save(account);
    }
}