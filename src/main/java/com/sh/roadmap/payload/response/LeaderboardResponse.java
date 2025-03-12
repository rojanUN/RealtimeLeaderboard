package com.sh.roadmap.payload.response;

import lombok.Data;

@Data
public class LeaderboardResponse {
    private String game;
    private String username;
    private String submittedAt;
    private long score;
}
