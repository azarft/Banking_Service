package com.effectiveMobile.bankingservice.controllers;

import com.effectiveMobile.bankingservice.dto.entityDto.UserDto;
import com.effectiveMobile.bankingservice.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@Log4j2
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.findAllUsers();
    }
    @GetMapping("/user/{id}")
    public Optional<UserDto> getUserById(@PathVariable Long id){
        return userService.findUserByID(id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
