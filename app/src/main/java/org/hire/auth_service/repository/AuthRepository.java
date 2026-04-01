package org.hire.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.hire.auth_service.model.AuthEntity;

@Repository
public interface AuthRepository extends JpaRepository<AuthEntity, Long> {
    
    @Query("SELECT a FROM AuthEntity a WHERE a.userId = :userId")
    AuthEntity findByUserId(Long userId);
    
    @Query("SELECT a FROM AuthEntity a WHERE a.userId = :userId AND a.password = :password")
    AuthEntity findByUserIdAndPassword(Long userId, String password);
}
