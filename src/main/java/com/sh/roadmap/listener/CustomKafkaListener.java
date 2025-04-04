package com.sh.roadmap.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomKafkaListener {

    @KafkaListener(topics = "dbserver1.users.users")
    public void handleChangeEvent(ConsumerRecord<String, String> record) {
        log.info("Received change event: {}", record.value());
        // Parse JSON and process the change
    }
}
