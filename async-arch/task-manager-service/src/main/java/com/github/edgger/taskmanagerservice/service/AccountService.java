package com.github.edgger.taskmanagerservice.service;

import com.github.edgger.AccountCreatedMsgV1;
import com.github.edgger.AccountRoleChangedMsgV1;
import com.github.edgger.taskmanagerservice.entity.Account;
import com.github.edgger.taskmanagerservice.entity.AccountRole;
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

    public void addNewAccount(AccountCreatedMsgV1 evt) {
        Account account = new Account(UUID.fromString(evt.getAccountId().toString()),
                AccountRole.valueOf(evt.getAccountRole().toString()));
        accountRepository.save(account);
    }

    public void changeAccountRole(AccountRoleChangedMsgV1 evt) {
        accountRepository.updateAccountRoleById(UUID.fromString(evt.getAccountId().toString()),
                AccountRole.valueOf(evt.getAccountRole().toString()));
    }
}
