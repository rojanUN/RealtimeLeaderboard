package com.sh.roadmap.service.impl;

import com.sh.roadmap.payload.response.GameResponse;
import com.sh.roadmap.payload.request.GameUpdateRequest;
import com.sh.roadmap.builder.ResponseBuilder;
import com.sh.roadmap.entity.GameEntity;
import com.sh.roadmap.enums.StatusEnum;
import com.sh.roadmap.exception.LeaderboardException;
import com.sh.roadmap.model.Response;
import com.sh.roadmap.payload.request.GameRequest;
import com.sh.roadmap.repository.GameRepository;
import com.sh.roadmap.service.GameService;
import com.sh.roadmap.util.CommonUtil;
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
        new ModelMapper().map(request, gameEntity);
        gameRepository.save(gameEntity);
        return ResponseBuilder.buildSuccessResponse("message.game.update.success");

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
}
