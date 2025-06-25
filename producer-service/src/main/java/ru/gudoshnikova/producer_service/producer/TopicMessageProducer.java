package ru.gudoshnikova.producer_service.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class TopicMessageProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.message}")
    private String messageTopic;

    public TopicMessageProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        System.out.println(message);
        kafkaTemplate.send(messageTopic, UUID.randomUUID().toString(), message);
        System.out.println(message);
    }
}
