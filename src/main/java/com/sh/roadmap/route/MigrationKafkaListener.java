package com.sh.roadmap.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MigrationKafkaListener extends RouteBuilder {
    @Override
    public void configure() throws Exception {
//        from("from:NOTIFICATION-LOCAL?brokers=localhost:9092")
//                .to("kafka:NOTIFICATION-LOCAL?brokers=localhost:9092");
    }
}
