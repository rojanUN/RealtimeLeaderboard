package com.sh.roadmap.payload.request;

import lombok.Data;

@Data
public class PaginationRequest {
    Integer pageNo;
    Integer pageSize;
    String sortBy;
    String direction;
}
