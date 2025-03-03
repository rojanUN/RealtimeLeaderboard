package com.sh.roadmap.service;

import com.sh.roadmap.entity.UserEntity;
import com.sh.roadmap.exception.LeaderboardException;
import com.sh.roadmap.model.Response;
import com.sh.roadmap.payload.RegisterUserRequest;
import com.sh.roadmap.payload.request.AuthenticateRequest;

public interface AuthService {
    Response createUser(RegisterUserRequest request) throws LeaderboardException;

    Response authenticate(AuthenticateRequest request) throws LeaderboardException;
}
