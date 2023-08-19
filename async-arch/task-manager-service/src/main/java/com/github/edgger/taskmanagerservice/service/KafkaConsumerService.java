package com.github.edgger.taskmanagerservice.service;

import com.github.edgger.taskmanagerservice.dto.kafka.AccountCreatedEvt;
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

    private final AccountService accountService;

    @KafkaListener(topics = {"${app.kafka.consumer.topics.account-created}"},
            groupId = "task-manager-service.account-created",
            properties = {"spring.json.value.default.type=com.github.edgger.taskmanagerservice.dto.kafka.AccountCreatedEvt"})
    public void consume(@Payload AccountCreatedEvt payload,
                        @Headers Map<String, String> headers,
                        Acknowledgment acknowledgment) {
        log.info("=> consumed {}", payload);
        accountService.addNewAccountFromEvent(payload);
        acknowledgment.acknowledge();
    }
}
