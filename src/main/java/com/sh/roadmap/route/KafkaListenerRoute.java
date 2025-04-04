package com.sh.roadmap.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
//        from("kafka:NOTIFICATION-LOCAL?brokers=localhost:9092")
//                .log("ReceivedMessage: ${body}")
//                .process(new KafkaTemplateProcessor());
//                .to("bean:")
    }

}