package com.github.edgger.billingservice.service;

import com.github.edgger.AccountCreatedMsgV1;
import com.github.edgger.AccountRoleChangedMsgV1;
import com.github.edgger.billingservice.entity.Account;
import com.github.edgger.billingservice.entity.AccountRole;
import com.github.edgger.billingservice.repository.AccountRepository;
import com.github.edgger.billingservice.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class AccountEventProcessorService {

    private AccountRepository accountRepository;
    private TaskRepository taskRepository;

    public void addNewAccount(AccountCreatedMsgV1 evt) {
        UUID accountId = UUID.fromString(evt.getAccountId().toString());
        Account account = new Account(accountId,
                AccountRole.valueOf(evt.getAccountRole().toString()),
                new BigDecimal(0));
        accountRepository.save(account);
    }

    public void changeAccountRole(AccountRoleChangedMsgV1 evt) {
        accountRepository.updateAccountRoleById(UUID.fromString(evt.getAccountId().toString()),
                AccountRole.valueOf(evt.getAccountRole().toString()));
    }
}
