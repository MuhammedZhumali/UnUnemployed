package org.hire.auth_service.service;

import org.hire.auth_service.model.AuthEntity;
import org.hire.auth_service.repository.AuthRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if(!PasswordHasher.verifyPassword(password, authEntity.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }
    }
    
}
