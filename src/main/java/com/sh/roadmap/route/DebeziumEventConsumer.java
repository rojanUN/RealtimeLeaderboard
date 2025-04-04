package com.sh.roadmap.route;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DebeziumEventConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "dbserver1.users.users")
    public void consumeChangeEvent(String message, Acknowledgment ack) {
        try {
            JsonNode event = objectMapper.readTree(message);

            // Extract operation type (c=create, u=update, d=delete)
            String op = event.path("op").asText();
            JsonNode before = event.path("before");  // Old state (for updates/deletes)
            JsonNode after = event.path("after");    // New state (for creates/updates)

            switch (op) {
                case "c":
                    handleCreate(after);
                    break;
                case "u":
                    handleUpdate(before, after);
                    break;
                case "d":
                    handleDelete(before);
                    break;
                default:
                    log.warn("Unknown operation type: {}", op);
            }

            ack.acknowledge();  // Manual commit
        } catch (Exception e) {
            log.error("Error processing CDC event: {}", message, e);
        }
    }

    private void handleCreate(JsonNode data) {
        log.info("New record created: {}", data);
        // Example: Save to another database or trigger business logic
    }

    private void handleUpdate(JsonNode before, JsonNode after) {
        log.info("Record updated - Before: {}, After: {}", before, after);
        // Example: Sync changes to Elasticsearch
    }

    private void handleDelete(JsonNode data) {
        log.info("Record deleted: {}", data);
        // Example: Remove from cache
    }

}
