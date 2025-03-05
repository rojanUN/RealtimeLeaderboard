package com.sh.roadmap.service.impl;

import com.sh.roadmap.model.Response;
import com.sh.roadmap.repository.ScoreRepository;
import com.sh.roadmap.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {

    private final ScoreRepository scoreRepository;

    @Override
    public Response submitScore() {
        return null;
    }
}
