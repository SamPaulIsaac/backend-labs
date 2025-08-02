package com.sam.basickafka.controller;

import com.sam.basickafka.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @GetMapping("/produce/{message}")
    public String produceMessage(@PathVariable String message) {
        kafkaProducerService.sendMessage(message);
        return "Message sent to Kafka: " + message;
    }
}

