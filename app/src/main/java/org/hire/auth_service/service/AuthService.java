package org.hire.auth_service.service;

import org.hire.auth_service.model.AuthEntity;
import org.hire.auth_service.repository.AuthRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void registerUser(Long userId, String password){
        String hashedPassword = PasswordHasher.hashPassword(password);
        AuthEntity authEntity = new AuthEntity();
        authEntity.setUserId(userId);
        authEntity.setPassword(hashedPassword);
        authRepository.save(authEntity);
    }

    public void login(Long userId, String password){
        AuthEntity authEntity = authRepository.findByUserId(userId);
        if(authEntity == null){
            throw new RuntimeException("User not found");
        }
        if(!PasswordHasher.hashPassword(password).equals(authEntity.getPassword())){
            throw new RuntimeException("Invalid password");
        }
    }
    
}
