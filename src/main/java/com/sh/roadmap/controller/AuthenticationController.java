package com.sh.roadmap.controller;

import com.sh.roadmap.annotation.AuthenticationRestController;
import com.sh.roadmap.exception.LeaderboardException;
import com.sh.roadmap.model.Response;
import com.sh.roadmap.payload.request.RegisterUserRequest;
import com.sh.roadmap.payload.request.AuthenticateRequest;
import com.sh.roadmap.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AuthenticationRestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@Valid @RequestBody RegisterUserRequest request) throws LeaderboardException {
        return ResponseEntity.ok(authService.createUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody AuthenticateRequest request) throws LeaderboardException {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
