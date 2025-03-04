package com.sh.roadmap.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Value;

/**
 * DTO for {@link com.sh.roadmap.entity.GameEntity}
 */

@Value
public class GameRequest {
    @NotEmpty
    String name;
    @Min(1)
    @Max(5)
    int gameRating;
    @NotEmpty
    String description;
}