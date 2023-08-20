package com.github.edgger.authservice.service;

import com.github.edgger.AccountCreatedMsgV1;
import com.github.edgger.AccountRoleChangedMsgV1;
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

    private final KafkaTemplate<String, AccountCreatedMsgV1> kafkaTemplateAccountCreated;

    private final KafkaTemplate<String, AccountRoleChangedMsgV1> kafkaTemplateAccountRoleChanged;

    @Value("${app.kafka.producer.topics.account-created}")
    private String topicAccountCreated;

    @Value("${app.kafka.producer.topics.account-role-changed}")
    private String topicAccountRoleChanged;

    public void sendAccountCreatedEvent(AccountCreatedMsgV1 evt) {
        ProducerRecord<String, AccountCreatedMsgV1> record = new ProducerRecord<>(topicAccountCreated, evt.getAccountId().toString(), evt);
        CompletableFuture<SendResult<String, AccountCreatedMsgV1>> future = kafkaTemplateAccountCreated.send(record);
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

    public void sendAccountRoleChangedEvent(AccountRoleChangedMsgV1 evt) {
        ProducerRecord<String, AccountRoleChangedMsgV1> record = new ProducerRecord<>(topicAccountRoleChanged, evt.getAccountId().toString(), evt);
        CompletableFuture<SendResult<String, AccountRoleChangedMsgV1>> future = kafkaTemplateAccountRoleChanged.send(record);
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
