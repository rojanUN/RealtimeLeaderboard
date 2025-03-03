package com.sh.roadmap.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private String token;
    private long expiresIn;
}
