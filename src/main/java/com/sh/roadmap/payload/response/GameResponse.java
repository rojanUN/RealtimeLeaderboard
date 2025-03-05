package com.sh.roadmap.payload.response;

import lombok.Data;

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