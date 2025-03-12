package com.sh.roadmap.service.specification;

import com.sh.roadmap.entity.ScoreEntity;
import com.sh.roadmap.payload.request.LeaderboardGetRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class LeaderboardSpecification {

    public static Specification<ScoreEntity> specification(LeaderboardGetRequest request) {
        return (root, query, criteriaBuilder) -> {
            Predicate finalPredicate = criteriaBuilder.conjunction();

            //gameId filter
            if (request.getGameId() != null) {
                finalPredicate = criteriaBuilder.equal(root.get("game").get("id"), request.getGameId());
            }

            return finalPredicate;
        };
    }

}
