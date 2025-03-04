package com.sh.roadmap.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class AuthenticateRequest {
    @NotNull
    String email;
    @NotNull
    String password;
}
