package org.hire.job_platform_service.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.hire.job_platform_service.domain.model.User;


public interface UserRepository extends JpaRepository<User, Long>{
    
}
