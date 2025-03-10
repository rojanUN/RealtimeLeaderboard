package com.sh.roadmap.entity;

import com.sh.roadmap.enums.StatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
    private long maxScore;

    @OneToMany(mappedBy = "game")
    private List<ScoreEntity> score;


    @PrePersist
    public void setDefaults() {
        if (this.status == null) {
            this.status = StatusEnum.ACTIVE;
        }
        if (this.maxScore == 0) {
            this.maxScore = 1000;
        }
    }

}
