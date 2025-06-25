package ru.gudoshnikova.consumer_service.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopicMessageListener {

    @KafkaListener(topics = "${spring.kafka.topic.message}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeAppointment(@Payload String message,
                                   @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        log.info("Received message: {}, key: {}", message, key);
    }
}
