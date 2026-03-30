package org.hire.job_platform_service.controller;

import org.springframework.web.bind.annotation.*;
import org.hire.job_platform_service.service.UserService;
import org.hire.job_platform_service.domain.model.User;
import org.hire.job_platform_service.dto.UserDto;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/get/{id}")
    public UserDto getById(@PathVariable Long id){
        return userService.getById(id);
    }

    @PostMapping("/create")
    public UserDto createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
