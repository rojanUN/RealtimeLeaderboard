package com.sh.roadmap.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterUserRequest {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @Email
    private String email;
}
