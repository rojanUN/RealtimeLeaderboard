package com.sh.roadmap.service.impl;

import com.sh.roadmap.builder.ResponseBuilder;
import com.sh.roadmap.entity.GameEntity;
import com.sh.roadmap.entity.ScoreEntity;
import com.sh.roadmap.entity.UserEntity;
import com.sh.roadmap.exception.LeaderboardException;
import com.sh.roadmap.model.Response;
import com.sh.roadmap.payload.request.LeaderboardGetRequest;
import com.sh.roadmap.payload.request.ScoreRequest;
import com.sh.roadmap.payload.response.LeaderboardResponse;
import com.sh.roadmap.repository.GameRepository;
import com.sh.roadmap.repository.ScoreRepository;
import com.sh.roadmap.service.LeaderboardService;
import com.sh.roadmap.service.specification.LeaderboardSpecification;
import com.sh.roadmap.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {

    private final ScoreRepository scoreRepository;
    private final GameRepository gameRepository;

    @Override
    public Response submitScore(ScoreRequest request) throws LeaderboardException {

        GameEntity game = gameRepository.findById(request.getGameId()).orElseThrow(() -> new LeaderboardException("GAM002"));
        if (request.getScore() > game.getMaxScore()) {
            throw new LeaderboardException("GAM003");
        }
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

    @Override
    @Cacheable(value = "leaderboard", key = "#request.gameId")
    public Response getLeaderboard(LeaderboardGetRequest request) throws LeaderboardException {

        Specification<ScoreEntity> specification = LeaderboardSpecification.specification(request);
        Page<ScoreEntity> scoreEntityPage = scoreRepository.findAll(specification, CommonUtil.getPageable(request));
        List<ScoreEntity> gameScores = scoreEntityPage.getContent();
        List<LeaderboardResponse> leaderboardResponses = gameScores.stream()
                .map(e -> {
                    LeaderboardResponse response = new LeaderboardResponse();
                    response.setScore(e.getScore());
                    response.setSubmittedAt(e.getCreatedAt().toString());
                    response.setGame(e.getGame().getName());
                    response.setUsername(e.getUser().getUsername());
                    return response;
                })
                .toList();
        return ResponseBuilder.buildSuccessResponse("message.score.get.success", leaderboardResponses);
    }
}
