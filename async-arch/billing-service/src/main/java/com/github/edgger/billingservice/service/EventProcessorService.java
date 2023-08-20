package com.github.edgger.billingservice.service;

import com.github.edgger.AccountCreatedMsgV1;
import com.github.edgger.TaskAssignedMsgV1;
import com.github.edgger.TaskCompletedMsgV1;
import com.github.edgger.TaskCreatedMsgV1;
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
public class EventProcessorService {

    private AccountRepository accountRepository;
    private TaskRepository taskRepository;

    public void addNewAccount(AccountCreatedMsgV1 evt) {
        UUID accountId = UUID.fromString(evt.getAccountId().toString());
        Account account = new Account(accountId,
                AccountRole.valueOf(evt.getAccountRole().toString()),
                new BigDecimal(0));
        accountRepository.save(account);
    }

    public void addNewTask(TaskCreatedMsgV1 payload) {
        //todo
    }

    public void assignTask(TaskAssignedMsgV1 payload) {
        //todo
    }

    public void completeTask(TaskCompletedMsgV1 payload) {
        //todo
    }
}
