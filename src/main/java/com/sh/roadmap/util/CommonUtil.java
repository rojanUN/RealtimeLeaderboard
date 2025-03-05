package com.sh.roadmap.util;

import com.sh.roadmap.entity.UserEntity;
import com.sh.roadmap.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonUtil {
    private final UserRepository userRepository;

    public Map<UUID, String> getUsernamesByIds(List<UUID> userIds) {
        List<UserEntity> userEntities = userRepository.findAllById(userIds);
        return userEntities.stream()
                .collect(Collectors.toMap(UserEntity::getId, UserEntity::getName));
    }
}
