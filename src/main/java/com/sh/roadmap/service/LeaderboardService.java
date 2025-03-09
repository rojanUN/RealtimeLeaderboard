package com.sh.roadmap.service;

import com.sh.roadmap.exception.LeaderboardException;
import com.sh.roadmap.model.Response;
import com.sh.roadmap.payload.request.ScoreRequest;

public interface LeaderboardService {
    Response submitScore(ScoreRequest request) throws LeaderboardException;
}
