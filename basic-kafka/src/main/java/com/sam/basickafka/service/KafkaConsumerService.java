package com.sam.basickafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final String TOPIC_NAME = "example-topic";

    @KafkaListener(topics = TOPIC_NAME, groupId = "example-group")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}

