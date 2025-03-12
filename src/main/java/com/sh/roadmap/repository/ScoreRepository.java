package com.sh.roadmap.repository;

import com.sh.roadmap.entity.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface ScoreRepository extends JpaRepository<ScoreEntity, UUID>, JpaSpecificationExecutor<ScoreEntity> {
    List<ScoreEntity> findAllByGameId(UUID gameId);
}