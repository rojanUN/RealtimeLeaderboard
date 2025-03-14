package com.sh.roadmap.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class LeaderboardResponse {
    private String game;
    private List<ScoreResponse> scores;
}
