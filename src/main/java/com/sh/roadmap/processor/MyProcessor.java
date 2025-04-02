package com.sh.roadmap.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.roadmap.entity.PermissionEntity;
import com.sh.roadmap.model.Response;
import com.sh.roadmap.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyProcessor implements Processor {

    private final PermissionRepository permissionRepository;

    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);
        Response response = new ObjectMapper().readValue(body, Response.class);

        JsonNode node = new ObjectMapper().readTree(body);

        JsonNode dataNode = node.get("data");
        String name = dataNode.get("name").asText();
        String description = dataNode.get("description").asText();

        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setName(name);
        permissionEntity.setDescription(description);
        permissionRepository.save(permissionEntity);

    }
}
