package com.github.edgger.taskmanagerservice.service;

import com.github.edgger.taskmanagerservice.dto.kafka.AccountCreatedEvt;
import com.github.edgger.taskmanagerservice.entity.Account;
import com.github.edgger.taskmanagerservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public void addNewAccountFromEvent(AccountCreatedEvt evt){
        Account account = new Account(UUID.fromString(evt.getAccountId()), evt.getRole());
        accountRepository.save(account);
    }
}
