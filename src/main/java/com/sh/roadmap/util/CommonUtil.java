package com.sh.roadmap.util;

import com.sh.roadmap.entity.UserEntity;
import com.sh.roadmap.payload.request.PaginationRequest;
import com.sh.roadmap.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonUtil {
    private final UserRepository userRepository;

    public static Pageable getPageable(PaginationRequest request) {
        return PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by(Objects.equals(request.getDirection(), "asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                request.getSortBy() == null ? "score" : request.getSortBy()
        ));
    }


    public Map<UUID, String> getUsernamesByIds(List<UUID> userIds) {
        List<UserEntity> userEntities = userRepository.findAllById(userIds);
        return userEntities.stream()
                .collect(Collectors.toMap(UserEntity::getId, UserEntity::getName));
    }

    public static UserEntity getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }
}
