package com.effectiveMobile.bankingservice.services.email;

import com.effectiveMobile.bankingservice.dto.requests.ChangeEmailDto;
import com.effectiveMobile.bankingservice.dto.entityDto.EmailDto;

import java.util.List;
import java.util.Optional;

public interface EmailService {
    List<EmailDto> findAllEmails();
    EmailDto addEmail(EmailDto emailDto);
    void removeEmail(String email);
    Optional<EmailDto> changeEmail(ChangeEmailDto emailDto);
    Optional<EmailDto> getEmail(String email);
}
