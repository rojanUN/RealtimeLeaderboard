package com.sh.roadmap.payload.request;

import lombok.Value;

import java.util.UUID;

@Value
public class LeaderboardGetRequest extends PaginationRequest {
    String searchText;

    UUID gameId;
}
