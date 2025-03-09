package com.sh.roadmap.service.impl;

import com.sh.roadmap.builder.ResponseBuilder;
import com.sh.roadmap.entity.GameEntity;
import com.sh.roadmap.entity.ScoreEntity;
import com.sh.roadmap.entity.UserEntity;
import com.sh.roadmap.exception.LeaderboardException;
import com.sh.roadmap.model.Response;
import com.sh.roadmap.payload.request.ScoreRequest;
import com.sh.roadmap.repository.GameRepository;
import com.sh.roadmap.repository.ScoreRepository;
import com.sh.roadmap.repository.UserRepository;
import com.sh.roadmap.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {

    private final ScoreRepository scoreRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Override
    public Response submitScore(ScoreRequest request) throws LeaderboardException {

        GameEntity game = gameRepository.findById(request.getGameId()).orElseThrow(() -> new LeaderboardException("GAM002"));
//        UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(() -> new LeaderboardException("USR001"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        ScoreEntity score = ScoreEntity.builder()
                .game(game)
                .score(request.getScore())
                .user(user)
                .build();
        scoreRepository.save(score);
        return ResponseBuilder.buildSuccessResponse("message.score.submit.success");
    }
}
