package com.example.walktogether.vo;

import com.example.walktogether.type.UserRole;
import jakarta.persistence.*;
import jdk.jfr.Registered;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String providerId;

    @Enumerated
    private UserRole role;

    @Builder
    public User(Long id, String username, String password, String providerId, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.providerId = providerId;
        this.role = role;
    }
}
