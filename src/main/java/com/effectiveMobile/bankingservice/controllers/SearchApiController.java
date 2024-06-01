package com.effectiveMobile.bankingservice.controllers;


import com.effectiveMobile.bankingservice.dto.entityDto.UserDto;
import com.effectiveMobile.bankingservice.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/search")
@Log4j2
@RequiredArgsConstructor
public class SearchApiController {
    private final UserService userService;

    @GetMapping
    @io.swagger.v3.oas.annotations.Operation
    public Page<UserDto> searchUsers(
            @RequestParam(required = false) LocalDate dateOfBirth,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @PageableDefault(size = 10) Pageable pageable) {

        return userService.searchUsers(dateOfBirth, phoneNumber, fullName, email, pageable);
    }
}
