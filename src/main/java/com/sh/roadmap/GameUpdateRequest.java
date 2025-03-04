package com.sh.roadmap;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.sh.roadmap.entity.GameEntity}
 */
@Value
public class GameUpdateRequest implements Serializable {
    String name;
    int gameRating;
    String description;
}