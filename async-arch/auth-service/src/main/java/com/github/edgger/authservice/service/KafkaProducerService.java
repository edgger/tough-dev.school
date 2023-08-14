package com.github.edgger.authservice.service;

import com.github.edgger.authservice.dto.kafka.AccountCreatedEvt;
import com.github.edgger.authservice.dto.kafka.AccountRoleChangedEvt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, AccountCreatedEvt> kafkaTemplateAccountCreated;

    private final KafkaTemplate<String, AccountRoleChangedEvt> kafkaTemplateAccountRoleChanged;

    @Value("${app.kafka.producer.topics.account-created}")
    private String topicAccountCreated;

    @Value("${app.kafka.producer.topics.account-role-changed}")
    private String topicAccountRoleChanged;

    public void sendAccountCreatedEvent(AccountCreatedEvt evt) {
        ProducerRecord<String, AccountCreatedEvt> record = new ProducerRecord<>(topicAccountCreated, evt.getAccountId(), evt);
        CompletableFuture<SendResult<String, AccountCreatedEvt>> future = kafkaTemplateAccountCreated.send(record);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[" + evt +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                log.error("Unable to send message=[" +
                        evt + "] due to : " + ex.getMessage());
            }
        });
    }

    public void sendAccountRoleChangedEvent(AccountRoleChangedEvt evt) {
        ProducerRecord<String, AccountRoleChangedEvt> record = new ProducerRecord<>(topicAccountCreated, evt.getAccountId(), evt);
        CompletableFuture<SendResult<String, AccountRoleChangedEvt>> future = kafkaTemplateAccountRoleChanged.send(record);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[" + evt +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                log.error("Unable to send message=[" +
                        evt + "] due to : " + ex.getMessage());
            }
        });

    }
}
