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
import com.sh.roadmap.payload.response.ScoreResponse;
import com.sh.roadmap.repository.GameRepository;
import com.sh.roadmap.repository.ScoreRepository;
import com.sh.roadmap.repository.UserRepository;
import com.sh.roadmap.service.LeaderboardService;
import com.sh.roadmap.service.specification.LeaderboardSpecification;
import com.sh.roadmap.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {
    private final ScoreRepository scoreRepository;

    private final GameRepository gameRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;


    @Scheduled(cron = "0 0 * * * *")
    public void persistScoreToDb() throws LeaderboardException {
        List<UUID> gameIds = gameRepository.findAllIds();
        for (UUID gameId : gameIds) {
            Set<ZSetOperations.TypedTuple<Object>> redisSet = redisTemplate.opsForZSet().reverseRangeWithScores(String.valueOf(gameId), 0, 1000);
            for (ZSetOperations.TypedTuple<Object> tuple : Objects.requireNonNull(redisSet)) {
                UUID userId = UUID.fromString((String) Objects.requireNonNull(tuple.getValue()));
                Double score = tuple.getScore();


                GameEntity game = gameRepository.findById(gameId).orElseThrow(() -> new LeaderboardException("Game not found"));

                UserEntity user = userRepository.findById(userId).orElseThrow(() -> new LeaderboardException("User not found"));

                // Save to the database
                ScoreEntity scoreEntity = new ScoreEntity();
                scoreEntity.setUser(user);
                scoreEntity.setGame(game);
                scoreEntity.setScore(score);
                scoreRepository.save(scoreEntity);
            }
        }
    }

    @Override
    public Response submitScore(ScoreRequest request) throws LeaderboardException {
        GameEntity game = gameRepository.findById(request.getGameId()).orElseThrow(() -> new LeaderboardException("GAM002"));
        if (request.getScore() > game.getMaxScore()) {
            throw new LeaderboardException("GAM003");
        }
        UserEntity user = CommonUtil.getLoggedInUser();
        double randomScore = 1 + (int) (Math.random() * 1000);
        redisTemplate.opsForZSet().add(String.valueOf(request.getGameId()), user.getId(), randomScore);
        return ResponseBuilder.buildSuccessResponse("message.score.submit.success");
    }

//    @Scheduled(fixedRate = 1000)
    public void testPopulate() {
        List<UUID> userIds = userRepository.findAllIds();
        UUID gameId = UUID.fromString("b85e1aae-d7c5-4746-ac11-3ed3330361e9");
        userIds.forEach(userId -> {
            double randomScore = 1 + (int) (Math.random() * 1000);
            redisTemplate.opsForZSet().add(String.valueOf(gameId), userId, randomScore);
            ScoreResponse scoreResponse = new ScoreResponse();
            scoreResponse.setUsername(userRepository.findById(userId).get().getUsername());
            scoreResponse.setScore(randomScore);
//            scoreResponse.setRank(1); // Set the appropriate rank

            // Send the updated score to all subscribed clients
            messagingTemplate.convertAndSend("/topic/leaderboard", scoreResponse);
        });
    }

    @Override
    @Cacheable(value = "leaderboard", key = "#request.gameId")
//    @Cacheable(value = "leaderboards")
    public Response getLeaderboard(LeaderboardGetRequest request) throws LeaderboardException {
        Specification<ScoreEntity> specification = LeaderboardSpecification.specification(request);
        Page<ScoreEntity> scoreEntityPage = scoreRepository.findAll(specification, CommonUtil.getPageable(request));
        List<ScoreEntity> gameScores = scoreEntityPage.getContent();
        List<ScoreResponse> scoreResponses = gameScores.stream().map(e -> {
            ScoreResponse response = new ScoreResponse();
            response.setScore(e.getScore());
//            response.setSubmittedAt(e.getCreatedAt().toString());
//            response.setGame(e.getGame().getName());
            response.setUsername(e.getUser().getUsername());
            return response;
        }).toList();
        return ResponseBuilder.buildSuccessResponseWithCode("message.score.get.success", scoreResponses);
    }

    //get leaderboard current rankings from redis sorted sets
    @Override
    public Response leaderboard(LeaderboardGetRequest request) throws LeaderboardException {
        //get sorted set
        Set<ZSetOperations.TypedTuple<Object>> redisSet = redisTemplate.opsForZSet()
                .reverseRangeWithScores(String.valueOf(request.getGameId()), 0, (long) request.getPageSize() - 1);

        //find the game name
        GameEntity game = gameRepository.findById(request.getGameId()).orElseThrow(() -> new LeaderboardException("GAM002"));
        String gameName = game.getName();

        //create leaderboard
        List<ScoreResponse> scoreResponses = new ArrayList<>();
        int rank = 1;
        for (ZSetOperations.TypedTuple<Object> tuple : Objects.requireNonNull(redisSet)) {
            if (tuple.getValue() != null) {
                UUID userId = UUID.fromString((String) tuple.getValue());
                Double score = tuple.getScore();

                UserEntity user = userRepository.findById(userId).orElseThrow(() -> new LeaderboardException(""));
                String username;
                if (user != null) {
                    username = user.getUsername();
                } else username = "anonymous";
                ScoreResponse response = new ScoreResponse();
                response.setRank(rank++);
                response.setUsername(username);
                response.setScore(score);
                scoreResponses.add(response);
            }
        }

        //build and response
        LeaderboardResponse leaderboardResponse = LeaderboardResponse.builder()
                .game(gameName)
                .scores(scoreResponses)
                .build();
        return ResponseBuilder.buildSuccessResponseWithCode("message.score.get.success", leaderboardResponse);
    }


}
