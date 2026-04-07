package org.hire.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.hire.auth_service.model.AuthEntity;

@Repository
public interface AuthRepository extends JpaRepository<AuthEntity, Long> {

    AuthEntity findByUserId(Long userId);

    AuthEntity findByUserIdAndPassword(Long userId, String password);
}
