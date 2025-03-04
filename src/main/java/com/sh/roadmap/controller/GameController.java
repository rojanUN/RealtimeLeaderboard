package com.sh.roadmap.controller;

import com.sh.roadmap.exception.LeaderboardException;
import com.sh.roadmap.model.Response;
import com.sh.roadmap.payload.request.GameRequest;
import com.sh.roadmap.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @PostMapping("/create")
    public ResponseEntity<Response> createGame(@RequestBody GameRequest request) throws LeaderboardException {
        return ResponseEntity.ok(gameService.createGame(request));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Response> findGame(@PathVariable UUID id) throws LeaderboardException {
        return ResponseEntity.ok(gameService.getGame(id));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Response> removeGame(@PathVariable UUID id) throws LeaderboardException {
        return ResponseEntity.ok(gameService.deleteGame(id));
    }

}
