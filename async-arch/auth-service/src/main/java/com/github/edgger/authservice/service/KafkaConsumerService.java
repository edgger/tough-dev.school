package com.github.edgger.authservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class KafkaConsumerService {

//    @KafkaListener(topics = {"${app.kafka.consumer.topic}"})
    public void consume(@Payload String payload,
                        @Headers Map<String, String> headers,
                        Acknowledgment acknowledgment) {
        log.info("=> consumed {}", payload);
        acknowledgment.acknowledge();
    }
}
