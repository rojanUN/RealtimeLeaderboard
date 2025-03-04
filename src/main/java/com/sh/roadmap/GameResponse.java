package com.sh.roadmap;

import lombok.Data;

import java.util.UUID;

/**
 * DTO for {@link com.sh.roadmap.entity.GameEntity}
 */
@Data
public class GameResponse {
    String createdAt;
    String createdBy;
    String lastModifiedBy;
    String name;
    int gameRating;
    String description;
}