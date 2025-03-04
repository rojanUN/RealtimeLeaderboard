package com.sh.roadmap.entity;

import com.sh.roadmap.enums.StatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "game")
public class GameEntity extends AbstractBaseEntity {
    private String name;
    private int gameRating;
    private StatusEnum status;
    private String description;

    @OneToMany(mappedBy = "game")
    private List<ScoreEntity> score;
}
