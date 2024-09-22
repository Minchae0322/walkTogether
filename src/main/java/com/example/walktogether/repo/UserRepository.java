package com.example.walktogether.repo;

import com.example.walktogether.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndProviderId(String username, String providerId);
}
