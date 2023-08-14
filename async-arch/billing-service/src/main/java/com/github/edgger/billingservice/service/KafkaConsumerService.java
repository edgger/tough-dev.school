package com.github.edgger.billingservice.service;

import com.github.edgger.billingservice.dto.kafka.AccountCreatedEvt;
import com.github.edgger.billingservice.dto.kafka.TaskAssignedEvt;
import com.github.edgger.billingservice.dto.kafka.TaskCompletedEvt;
import com.github.edgger.billingservice.dto.kafka.TaskCreatedEvt;
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

    private final EventProcessorService eventProcessorService;

    @KafkaListener(topics = {"${app.kafka.consumer.topics.account-created}"},
            groupId = "billing-service.account-created",
            properties = {"spring.json.value.default.type=com.github.edgger.billingservice.dto.kafka.AccountCreatedEvt"})
    public void consume(@Payload AccountCreatedEvt payload,
                        @Headers Map<String, String> headers,
                        Acknowledgment acknowledgment) {
        log.info("=> consumed {}", payload);
        eventProcessorService.addNewAccount(payload);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = {"${app.kafka.consumer.topics.task-created}"},
            groupId = "billing-service.task-created")
    public void consume(@Payload TaskCreatedEvt payload,
                        @Headers Map<String, String> headers,
                        Acknowledgment acknowledgment) {
        log.info("=> consumed {}", payload);
        eventProcessorService.addNewTask(payload);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = {"${app.kafka.consumer.topics.task-assigned}"},
            groupId = "billing-service.task-assigned")
    public void consume(@Payload TaskAssignedEvt payload,
                        @Headers Map<String, String> headers,
                        Acknowledgment acknowledgment) {
        log.info("=> consumed {}", payload);
        eventProcessorService.assignTask(payload);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = {"${app.kafka.consumer.topics.task-completed}"},
            groupId = "billing-service.task-completed")
    public void consume(@Payload TaskCompletedEvt payload,
                        @Headers Map<String, String> headers,
                        Acknowledgment acknowledgment) {
        log.info("=> consumed {}", payload);
        eventProcessorService.completeTask(payload);
        acknowledgment.acknowledge();
    }
}
