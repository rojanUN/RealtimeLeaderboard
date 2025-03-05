package com.sh.roadmap.repository;

import com.sh.roadmap.entity.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScoreRepository extends JpaRepository<ScoreEntity, UUID> {
}