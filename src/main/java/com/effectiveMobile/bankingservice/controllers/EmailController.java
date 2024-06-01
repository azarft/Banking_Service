package com.effectiveMobile.bankingservice.controllers;

import com.effectiveMobile.bankingservice.dto.requests.ChangeEmailDto;
import com.effectiveMobile.bankingservice.dto.entityDto.EmailDto;
import com.effectiveMobile.bankingservice.exceptions.NotFoundException;
import com.effectiveMobile.bankingservice.services.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emails")
@Log4j2
@RequiredArgsConstructor


public class EmailController {
    private final EmailService emailService;

    @GetMapping
    @io.swagger.v3.oas.annotations.Operation
    public List<EmailDto> getAllEmails(){
        return emailService.findAllEmails();
    }


    @PostMapping("/add")
    @io.swagger.v3.oas.annotations.Operation
    public EmailDto addEmail(@RequestBody EmailDto emailDto) {
        return emailService.addEmail(emailDto);
    }

    @DeleteMapping("/delete/{email}")
    @io.swagger.v3.oas.annotations.Operation
    public void removeEmail(@PathVariable String email) {
        emailService.removeEmail(email);
    }

    @PutMapping("/update")
    @io.swagger.v3.oas.annotations.Operation
    public EmailDto changeEmail(@RequestBody ChangeEmailDto changeEmailDto) {
        return emailService.changeEmail(changeEmailDto)
                .orElseThrow(() -> new NotFoundException("Email not found: " + changeEmailDto.getOldEmail()));
    }

    @GetMapping("/email/{email}")
    @io.swagger.v3.oas.annotations.Operation
    public EmailDto getEmail(@PathVariable String email) {
        return emailService.getEmail(email)
                .orElseThrow(() -> new NotFoundException("Email not found: " + email));
    }
}
