package com.sh.roadmap.controller;

import com.sh.roadmap.route.SimpleRouteBuilder;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jpa.JpaComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public void runTest() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("camel");
        JpaComponent jpaComponent = new JpaComponent();
        jpaComponent.setEntityManagerFactory(emf);


        try (CamelContext context = new DefaultCamelContext()) {
            context.addRoutes(new SimpleRouteBuilder());
            context.start();
            Thread.sleep(5000);
            context.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
