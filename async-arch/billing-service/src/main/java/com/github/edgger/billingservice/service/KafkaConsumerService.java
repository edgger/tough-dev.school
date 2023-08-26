package com.github.edgger.billingservice.service;

import com.github.edgger.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumerService {

    private final AccountEventProcessorService accountEventProcessorService;
    private final TaskEventProcessorService taskEventProcessorService;

    @KafkaListener(topics = {"${app.kafka.consumer.topics.account-created}"},
            groupId = "billing-service.account-created")
    public void consume(@Payload AccountCreatedMsgV1 payload,
                        @Headers Map<String, String> headers,
                        Acknowledgment acknowledgment) {
        log.info("=> consumed {}", payload);
        accountEventProcessorService.addNewAccount(payload);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = {"${app.kafka.consumer.topics.account-role-changed}"},
            groupId = "billing-service.account-created")
    public void consume(@Payload AccountRoleChangedMsgV1 payload,
                        @Headers Map<String, String> headers,
                        Acknowledgment acknowledgment) {
        log.info("=> consumed {}", payload);
        accountEventProcessorService.changeAccountRole(payload);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = {"${app.kafka.consumer.topics.task-created}"},
            groupId = "billing-service.task-created")
    public void consume(@Payload TaskCreatedMsgV1 payload,
                        @Headers Map<String, String> headers,
                        Acknowledgment acknowledgment) {
        log.info("=> consumed {}", payload);
        taskEventProcessorService.addNewTask(payload);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = {"${app.kafka.consumer.topics.task-created}"},
            groupId = "billing-service.task-created")
    public void consume(@Payload TaskCreatedMsgV2 payload,
                        @Headers Map<String, String> headers,
                        Acknowledgment acknowledgment) {
        log.info("=> consumed {}", payload);
        taskEventProcessorService.addNewTask(payload);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = {"${app.kafka.consumer.topics.task-assigned}"},
            groupId = "billing-service.task-assigned")
    public void consume(@Payload TaskAssignedMsgV1 payload,
                        @Headers Map<String, String> headers,
                        Acknowledgment acknowledgment) {
        log.info("=> consumed {}", payload);
        taskEventProcessorService.assignTask(payload);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = {"${app.kafka.consumer.topics.task-completed}"},
            groupId = "billing-service.task-completed")
    public void consume(@Payload TaskCompletedMsgV1 payload,
                        @Headers Map<String, String> headers,
                        Acknowledgment acknowledgment) {
        log.info("=> consumed {}", payload);
        taskEventProcessorService.completeTask(payload);
        acknowledgment.acknowledge();
    }
}
