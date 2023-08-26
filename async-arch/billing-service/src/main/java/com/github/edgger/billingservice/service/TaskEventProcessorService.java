package com.github.edgger.billingservice.service;

import com.github.edgger.TaskAssignedMsgV1;
import com.github.edgger.TaskCompletedMsgV1;
import com.github.edgger.TaskCreatedMsgV1;
import com.github.edgger.TaskCreatedMsgV2;
import com.github.edgger.billingservice.entity.Account;
import com.github.edgger.billingservice.entity.Task;
import com.github.edgger.billingservice.repository.AccountRepository;
import com.github.edgger.billingservice.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.random.RandomGenerator;

@RequiredArgsConstructor
@Slf4j
@Service
public class TaskEventProcessorService {

    private AccountRepository accountRepository;
    private TaskRepository taskRepository;

    @Transactional
    public void addNewTask(TaskCreatedMsgV1 payload) {
        Account account = accountRepository.getReferenceById(UUID.fromString(payload.getAccountId().toString()));
        BigDecimal costAssign = new BigDecimal(RandomGenerator.getDefault().nextInt(10, 20));
        BigDecimal costComplete = new BigDecimal(RandomGenerator.getDefault().nextInt(20, 40));
        Task task = new Task(null, null, null, payload.getDescription().toString(), account, costAssign, costComplete);
        task = taskRepository.save(task);
        //todo withdrawal
    }

    @Transactional
    public void addNewTask(TaskCreatedMsgV2 payload) {
        Account account = accountRepository.getReferenceById(UUID.fromString(payload.getAccountId().toString()));
        BigDecimal costAssign = new BigDecimal(RandomGenerator.getDefault().nextInt(10, 20));
        BigDecimal costComplete = new BigDecimal(RandomGenerator.getDefault().nextInt(20, 40));
        Task task = new Task(null, payload.getTitle().toString(),
                payload.getJiraId().toString(),
                payload.getDescription().toString(),
                account, costAssign, costComplete);
        task = taskRepository.save(task);
        //todo withdrawal
    }

    public void assignTask(TaskAssignedMsgV1 payload) {
        //todo
    }

    public void completeTask(TaskCompletedMsgV1 payload) {
        //todo
    }
}
