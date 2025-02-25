package com.sh.roadmap.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends AbstractBaseEntity implements UserDetails {
    private String username;

    private String password;

    private String email;

    private LocalDateTime lastPasswordChange;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @OneToMany(mappedBy = "user")
    private List<ScoreEntity> score;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }
}
