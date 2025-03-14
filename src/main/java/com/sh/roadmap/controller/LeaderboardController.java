package com.sh.roadmap.controller;

import com.sh.roadmap.exception.LeaderboardException;
import com.sh.roadmap.model.Response;
import com.sh.roadmap.payload.request.LeaderboardGetRequest;
import com.sh.roadmap.payload.request.ScoreRequest;
import com.sh.roadmap.service.LeaderboardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @PostMapping("/submit-score")
    public ResponseEntity<Response> submitScore(@Valid @RequestBody ScoreRequest request) throws LeaderboardException {
        return ResponseEntity.ok(leaderboardService.submitScore(request));
    }

    @PostMapping("/get")
    public ResponseEntity<Response> getLeaderboard(@RequestBody LeaderboardGetRequest request) throws LeaderboardException {
        return ResponseEntity.ok(leaderboardService.leaderboard(request));
    }

}
