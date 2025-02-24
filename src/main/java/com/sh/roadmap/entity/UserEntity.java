package com.sh.roadmap.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "users")
public class UserEntity extends AbstractBaseEntity {

    private String username;

    private String password;

    private String email;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @OneToMany(mappedBy = "user")
    private List<ScoreEntity> score;

}
