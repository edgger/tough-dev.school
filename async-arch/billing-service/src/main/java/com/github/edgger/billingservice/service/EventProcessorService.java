package com.github.edgger.billingservice.service;

import com.github.edgger.billingservice.dto.kafka.AccountCreatedEvt;
import com.github.edgger.billingservice.dto.kafka.TaskAssignedEvt;
import com.github.edgger.billingservice.dto.kafka.TaskCompletedEvt;
import com.github.edgger.billingservice.dto.kafka.TaskCreatedEvt;
import com.github.edgger.billingservice.entity.Account;
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

    public void addNewAccount(AccountCreatedEvt evt) {
        UUID accountId = UUID.fromString(evt.getAccountId());
        Account account = new Account(accountId, evt.getRole(), new BigDecimal(0));
        accountRepository.save(account);
    }

    public void addNewTask(TaskCreatedEvt payload) {
        //todo
    }

    public void assignTask(TaskAssignedEvt payload) {
        //todo
    }

    public void completeTask(TaskCompletedEvt payload) {
        //todo
    }
}
