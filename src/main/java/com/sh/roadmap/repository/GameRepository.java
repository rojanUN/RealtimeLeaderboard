package com.sh.roadmap.repository;

import com.sh.roadmap.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<GameEntity, UUID> {
    boolean existsByNameIgnoreCase(String name);

    @Query("select g.id from game g")
    List<UUID> findAllIds();
}
