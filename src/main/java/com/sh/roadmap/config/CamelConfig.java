package com.sh.roadmap.config;

import com.sh.roadmap.route.SimpleRouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

//    @Bean
//    public void startCamel(SimpleRouteBuilder simpleRouteBuilder) {W
//        try (CamelContext context = new DefaultCamelContext()) {
//            context.addRoutes(simpleRouteBuilder);
//            context.start();
//
//            Thread.sleep(5000);
//            context.stop();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
