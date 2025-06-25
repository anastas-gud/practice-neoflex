package ru.gudoshnikova.producer_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gudoshnikova.producer_service.producer.TopicMessageProducer;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final TopicMessageProducer topicMessageProducer;
    @PostMapping("/message")
    public ResponseEntity<Void> message(
            @RequestParam() String message) {
        topicMessageProducer.sendMessage(message);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
