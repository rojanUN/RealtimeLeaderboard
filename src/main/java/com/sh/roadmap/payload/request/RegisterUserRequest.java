package com.sh.roadmap.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Value;

@Value
public class RegisterUserRequest {
    @NotEmpty
    String username;
    @NotEmpty
    String password;
    @Email
    String email;
}
