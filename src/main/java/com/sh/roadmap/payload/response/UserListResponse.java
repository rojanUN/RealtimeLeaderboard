package com.sh.roadmap.payload.response;

import lombok.Data;

import java.util.UUID;

@Data
public class UserListResponse {
    private UUID id;
    private String username;
}
