package com.example.patterns_banking.services;

import com.example.patterns_banking.dtos.AccountDTO;
import com.example.patterns_banking.models.Account;
import com.example.patterns_banking.repositories.IAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService<IAccountService> {
    private IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account create(AccountDTO AccountDTO) {
        Account account = Account
                .builder()
                .number(AccountDTO.getNumber())
                .type(AccountDTO.getType())
                .balance(AccountDTO.getBalance())
                .isActive(AccountDTO.getIsActive())
                .build();

        return accountRepository.save(account);
    }
}
