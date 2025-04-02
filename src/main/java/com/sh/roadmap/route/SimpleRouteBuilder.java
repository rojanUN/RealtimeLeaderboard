package com.sh.roadmap.route;

import com.sh.roadmap.entity.PermissionEntity;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SimpleRouteBuilder extends RouteBuilder {

    private static final String TOKEN = "eyJhbGciOiJIUzM4NCJ9.eyJhdXRob3JpdGllcyI6WyJBRE1JTiJdLCJzdWIiOiJyb2phbjFAZ21haWwuY29tIiwiaWF0IjoxNzQzNTg0MDgzLCJleHAiOjE3NDM1ODQ0NDN9.Pvg-Hpbkj9nysBGzre7JBITq-Y4ibMefu2yGIQHdMR534zMzxNdQfzZNvTeNHD1-";

    @Override
    public void configure() {
        from("timer:fetchUser?period=5000")
//                .setHeader("Authorization", constant("Bearer YOUR_ACCESS_TOKEN"))
                .toD("http://localhost:8001/api/v1/user/d5baf169-796f-478a-9a74-76696e215a6e")
                .unmarshal().json()
                .process(exchange -> {

                    Map<String, Object> body = exchange.getIn().getBody(Map.class);
                    Map<String, Object> data = (Map<String, Object>) body.get("data");

                    if (data != null) {
                        PermissionEntity permissionEntity = new PermissionEntity();
                        permissionEntity.setName((String) data.get("name"));
                        permissionEntity.setDescription((String) data.get("description"));
                        exchange.getIn().setBody(permissionEntity);
                    } else {
                        exchange.setProperty("CamelFailureHandled", true);
                    }
                })
                .to("jpa:com.sh.roadmap.entity.PermissionEntity");
    }
}
