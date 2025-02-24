package com.sh.roadmap.service;

import com.sh.roadmap.entity.UserEntity;
import com.sh.roadmap.model.Response;

public interface UserService {

    Response createUser(UserEntity user);
}
