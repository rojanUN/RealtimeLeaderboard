package com.sh.roadmap.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity(name = "leaderboard")
public class LeaderboardEntity extends AbstractBaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @ManyToOne
    private ScoreEntity score;
}
