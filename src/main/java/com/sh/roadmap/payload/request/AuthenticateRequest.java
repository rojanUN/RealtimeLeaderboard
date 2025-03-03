package com.sh.roadmap.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthenticateRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
