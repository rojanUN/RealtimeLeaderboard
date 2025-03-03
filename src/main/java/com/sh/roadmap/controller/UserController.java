package com.sh.roadmap.controller;

import com.sh.roadmap.annotation.UserRestController;
import com.sh.roadmap.builder.ResponseBuilder;
import com.sh.roadmap.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@UserRestController
public class UserController {
    @GetMapping("protected")
    public ResponseEntity<Response> helloWorld() {
        return ResponseEntity.ok(ResponseBuilder.buildSuccessResponse("hello world"));
    }
}
