package com.sh.roadmap.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.util.UUID;

/**
 * DTO for {@link com.sh.roadmap.entity.ScoreEntity}
 */
@Value
public class ScoreRequest {
    @NotBlank
    long score;
    UUID userId;
    UUID gameId;
}