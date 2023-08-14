package com.github.edgger.taskmanagerservice.service;

import com.github.edgger.taskmanagerservice.dto.kafka.TaskAssignedEvt;
import com.github.edgger.taskmanagerservice.dto.kafka.TaskCompletedEvt;
import com.github.edgger.taskmanagerservice.dto.kafka.TaskCreatedEvt;
import com.github.edgger.taskmanagerservice.entity.Task;
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

    private final KafkaTemplate<String, TaskCreatedEvt> kafkaTemplateCreated;
    private final KafkaTemplate<String, TaskAssignedEvt> kafkaTemplateAssigned;
    private final KafkaTemplate<String, TaskCompletedEvt> kafkaTemplateCompleted;

    @Value("${app.kafka.producer.topics.task-created}")
    private String topicTaskCreated;

    @Value("${app.kafka.producer.topics.task-assigned}")
    private String topicTaskAssigned;

    @Value("${app.kafka.producer.topics.task-completed}")
    private String topicTaskCompleted;

    public void sendTaskCreatedEvent(TaskCreatedEvt evt) {
        ProducerRecord<String, TaskCreatedEvt> record = new ProducerRecord<>(topicTaskCreated, evt.getTaskId(), evt);
        CompletableFuture<SendResult<String, TaskCreatedEvt>> future = kafkaTemplateCreated.send(record);
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

    public void sendTaskAssignedEvent(TaskAssignedEvt evt) {
        ProducerRecord<String, TaskAssignedEvt> record = new ProducerRecord<>(topicTaskAssigned, evt.getTaskId(), evt);
        CompletableFuture<SendResult<String, TaskAssignedEvt>> future = kafkaTemplateAssigned.send(record);
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

    public void sendTaskCompletedEvent(TaskCompletedEvt evt) {
        ProducerRecord<String, TaskCompletedEvt> record = new ProducerRecord<>(topicTaskCompleted, evt.getTaskId(), evt);
        CompletableFuture<SendResult<String, TaskCompletedEvt>> future = kafkaTemplateCompleted.send(record);
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
