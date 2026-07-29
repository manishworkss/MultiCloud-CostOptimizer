package com.cloud.optimizer.repository;

import com.cloud.optimizer.model.UserMfa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMfaRepository extends JpaRepository<UserMfa, String> {
    Optional<UserMfa> findByUserId(String userId);
    void deleteByUserId(String userId);
}
