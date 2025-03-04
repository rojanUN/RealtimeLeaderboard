package com.sh.roadmap.repository;

import com.sh.roadmap.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRepository extends JpaRepository<GameEntity, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
