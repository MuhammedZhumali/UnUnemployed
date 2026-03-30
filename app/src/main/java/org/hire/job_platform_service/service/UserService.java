package org.hire.job_platform_service.service;

import org.springframework.stereotype.Service;
import org.hire.job_platform_service.domain.model.User;
import org.hire.job_platform_service.domain.repository.UserRepository;
import org.hire.job_platform_service.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserDto createUser(User user){
        User savedUser = userRepo.save(user);
        return toDto(savedUser);
    }

    public List<UserDto> getAllUsers(){
        return userRepo.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getById(Long id){
        return userRepo.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    public void deleteUser(Long id){
        userRepo.deleteById(id);
    }

    public UserDto toDto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        return dto;
    }
}
