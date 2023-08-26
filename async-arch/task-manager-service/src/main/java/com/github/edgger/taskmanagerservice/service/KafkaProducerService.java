package com.github.edgger.taskmanagerservice.service;

import com.github.edgger.TaskAssignedMsgV1;
import com.github.edgger.TaskCompletedMsgV1;
import com.github.edgger.TaskCreatedMsgV2;
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

    private final KafkaTemplate<String, TaskCreatedMsgV2> kafkaTemplateCreated;
    private final KafkaTemplate<String, TaskAssignedMsgV1> kafkaTemplateAssigned;
    private final KafkaTemplate<String, TaskCompletedMsgV1> kafkaTemplateCompleted;

    @Value("${app.kafka.producer.topics.task-created}")
    private String topicTaskCreated;

    @Value("${app.kafka.producer.topics.task-assigned}")
    private String topicTaskAssigned;

    @Value("${app.kafka.producer.topics.task-completed}")
    private String topicTaskCompleted;

    public void sendTaskCreatedEvent(TaskCreatedMsgV2 evt) {
        ProducerRecord<String, TaskCreatedMsgV2> record = new ProducerRecord<>(topicTaskCreated, evt.getTaskId().toString(), evt);
        CompletableFuture<SendResult<String, TaskCreatedMsgV2>> future = kafkaTemplateCreated.send(record);
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

    public void sendTaskAssignedEvent(TaskAssignedMsgV1 evt) {
        ProducerRecord<String, TaskAssignedMsgV1> record = new ProducerRecord<>(topicTaskAssigned, evt.getTaskId().toString(), evt);
        CompletableFuture<SendResult<String, TaskAssignedMsgV1>> future = kafkaTemplateAssigned.send(record);
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

    public void sendTaskCompletedEvent(TaskCompletedMsgV1 evt) {
        ProducerRecord<String, TaskCompletedMsgV1> record = new ProducerRecord<>(topicTaskCompleted, evt.getTaskId().toString(), evt);
        CompletableFuture<SendResult<String, TaskCompletedMsgV1>> future = kafkaTemplateCompleted.send(record);
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
