package com.sh.roadmap;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.sh.roadmap.entity.GameEntity}
 */
@Value
public class GameEntityDto implements Serializable {
    String createdBy;
    String lastModifiedBy;
    String name;
    int gameRating;
    String description;
}