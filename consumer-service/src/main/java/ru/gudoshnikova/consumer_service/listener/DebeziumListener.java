package ru.gudoshnikova.consumer_service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DebeziumListener {
    private static final Logger LOG = LoggerFactory.getLogger(DebeziumListener.class);

    @KafkaListener(
            topics = "dbserver1_public_users",
            groupId = "message-group-id",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeUserChanges(ConsumerRecord<String, String> record) {
        LOG.info("Received User change: Key = {}, Value = {}", record.key(), record.value());
        processChangeEvent(record.value(), "users");
    }

    @KafkaListener(
            topics = "dbserver1_public_orders",
            groupId = "message-group-id",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeOrderChanges(ConsumerRecord<String, String> record) {
        LOG.info("Received Order change: Key = {}, Value = {}", record.key(), record.value());
        processChangeEvent(record.value(), "orders");
    }

    private void processChangeEvent(String event, String table) {
        try {
            if (event == null) {
                LOG.info("Received tombstone message for DELETE operation in table: {}", table);
                return;
            }

            JsonNode rootNode = new ObjectMapper().readTree(event);

            JsonNode payloadNode = rootNode.path("payload");

            if (!payloadNode.isMissingNode()) {
                String operation = payloadNode.path("op").asText();
                LOG.info("Operation: {}", operation);

                // Обработка разных операций
                switch (operation) {
                    case "c":
                        JsonNode after = payloadNode.path("after");
                        LOG.info("CREATE in {}: {}", table, after);
                        break;
                    case "u":
                        JsonNode afterUpdate = payloadNode.path("after");
                        LOG.info("UPDATE in {}: {}", table, afterUpdate);
                        break;
                    case "d":
                        JsonNode before = payloadNode.path("before");
                        LOG.info("DELETE in {}: {}", table, before);
                        break;
                    default:
                        LOG.warn("Unknown operation: {}", operation);
                }
            } else {
                String operation = rootNode.path("op").asText();
                LOG.info("Direct operation: {}", operation);
            }
        } catch (Exception e) {
            LOG.error("Error processing event: {}", event, e);
        }
    }
}
