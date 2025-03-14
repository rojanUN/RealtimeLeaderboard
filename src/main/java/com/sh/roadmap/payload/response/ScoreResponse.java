package com.sh.roadmap.payload.response;

import lombok.Data;

@Data
public class ScoreResponse {
    private String username;
    //    private String submittedAt;
    private int rank;
    private Double score;
}
