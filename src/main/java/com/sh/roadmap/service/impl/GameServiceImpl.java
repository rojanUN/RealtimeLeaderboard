package com.sh.roadmap.service.impl;

import com.sh.roadmap.builder.ResponseBuilder;
import com.sh.roadmap.entity.GameEntity;
import com.sh.roadmap.enums.StatusEnum;
import com.sh.roadmap.exception.LeaderboardException;
import com.sh.roadmap.model.Response;
import com.sh.roadmap.payload.request.GameRequest;
import com.sh.roadmap.payload.request.GameUpdateRequest;
import com.sh.roadmap.payload.response.GameResponse;
import com.sh.roadmap.repository.GameRepository;
import com.sh.roadmap.service.GameService;
import com.sh.roadmap.util.CommonUtil;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    private final CommonUtil helper;

    @Override
    public Response createGame(GameRequest gameRequest) throws LeaderboardException {
        if (gameRepository.existsByNameIgnoreCase(gameRequest.getName())) {
            throw new LeaderboardException("GAM001");
        }
        GameEntity gameEntity = new ModelMapper().map(gameRequest, GameEntity.class);
        gameRepository.save(gameEntity);
        return ResponseBuilder
                .buildSuccessResponse("message.game.creation.success");
    }

    @Override
    public Response updateGame(UUID gameId, GameUpdateRequest request) throws LeaderboardException {
        GameEntity gameEntity = gameRepository.findById(gameId).orElseThrow(() -> new LeaderboardException("GAM002"));
        simulateConcurrentUpdate(gameId, "qwerty");
        new ModelMapper().map(request, gameEntity);
        gameRepository.save(gameEntity);
        return ResponseBuilder.buildSuccessResponse("message.game.update.success");

    }

    // Simulate concurrent updates to trigger OptimisticLockException
    public void simulateConcurrentUpdate(UUID gameId, String newName) {
        new Thread(() -> updateGameTransaction1(gameId, newName)).start();

        new Thread(() -> {
            try {
                updateGameTransaction2(gameId, newName);
            } catch (LeaderboardException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    // Simulate the first transaction that modifies the entity
    @Transactional
    public void updateGameTransaction1(UUID gameId, String newName) {
        GameEntity game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));

        try {

            game.setName(newName);
            game.setGameRating(90);
            gameRepository.save(game);
        } catch (OptimisticLockException e) {
            throw new RuntimeException(e);
        }
        // Modify entity and save it

        System.out.println("Transaction 1 committed: Game updated with new name.");
    }

    // Simulate the second transaction that modifies the same entity concurrently
    @Transactional
    public void updateGameTransaction2(UUID gameId, String newName) throws LeaderboardException {
        try {
            // Simulating a delay to ensure both transactions run concurrently
            Thread.sleep(1000);

            GameEntity game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));

            // Modify entity and attempt to save it
            game.setName(newName + "_updated");
            game.setGameRating(85);

            // This save should fail because of optimistic locking if the version has changed
            gameRepository.save(game);
        } catch (OptimisticLockException e) {
            // Catch and log the OptimisticLockException
            System.out.println("OptimisticLockException caught in Transaction 2: " + e.getMessage());
            throw new LeaderboardException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void simulateConcurrentUpdate(UUID gameId) throws LeaderboardException {
        GameEntity gameEntity = gameRepository.findById(gameId).orElseThrow(() -> new LeaderboardException("GAM002"));
        gameEntity.setDescription("Game Description");
        gameEntity.setName("Game Name");
        gameEntity.setGameRating((int) (Math.random() * 100));
        gameRepository.saveAndFlush(gameEntity);
    }

    @Override
    public Response deleteGame(UUID gameId) throws LeaderboardException {
        GameEntity gameEntity = gameRepository.findById(gameId).orElseThrow(() -> new LeaderboardException("GAM002"));
        gameEntity.setStatus(StatusEnum.INACTIVE);
        return ResponseBuilder.buildSuccessResponse("message.game.delete.success");
    }

    @Override
    public Response getGame(UUID gameId) throws LeaderboardException {
        GameEntity gameEntity = gameRepository.findById(gameId).orElseThrow(() -> new LeaderboardException("GAM002"));
        GameResponse gameResponse = new ModelMapper().map(gameEntity, GameResponse.class);
        List<UUID> userIds = List.of(gameEntity.getCreatedBy(), gameEntity.getLastModifiedBy());
        Map<UUID, String> usernames = helper.getUsernamesByIds(userIds);
        gameResponse.setCreatedBy(usernames.get(gameEntity.getCreatedBy()));
        gameResponse.setLastModifiedBy(usernames.get(gameEntity.getLastModifiedBy()));
        return ResponseBuilder.buildSuccessResponse(gameResponse);
    }

    @Override
    public Response getAllGames() throws LeaderboardException {
        List<GameEntity> games = gameRepository.findAll();
        List<GameResponse> gameResponses = games.stream()
                .map(e -> new ModelMapper().map(e, GameResponse.class))
                .toList();
        return ResponseBuilder.buildSuccessResponse(gameResponses);

    }
}
