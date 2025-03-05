package com.sh.roadmap.service;

import com.sh.roadmap.payload.request.GameUpdateRequest;
import com.sh.roadmap.exception.LeaderboardException;
import com.sh.roadmap.model.Response;
import com.sh.roadmap.payload.request.GameRequest;

import java.util.UUID;

public interface GameService {

    Response createGame(GameRequest gameRequest) throws LeaderboardException;

    Response updateGame(UUID gameId, GameUpdateRequest request) throws LeaderboardException;

    Response deleteGame(UUID gameId) throws LeaderboardException;

    Response getGame(UUID gameId) throws LeaderboardException;
}
