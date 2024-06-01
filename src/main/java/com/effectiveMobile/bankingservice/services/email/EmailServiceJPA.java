package com.effectiveMobile.bankingservice.services.email;

import com.effectiveMobile.bankingservice.dto.requests.ChangeEmailDto;
import com.effectiveMobile.bankingservice.dto.auth.CustomUserDetails;
import com.effectiveMobile.bankingservice.dto.entityDto.EmailDto;
import com.effectiveMobile.bankingservice.entities.Email;
import com.effectiveMobile.bankingservice.entities.User;
import com.effectiveMobile.bankingservice.exceptions.NotFoundException;
import com.effectiveMobile.bankingservice.mappers.EmailMapper;
import com.effectiveMobile.bankingservice.repositories.EmailRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmailServiceJPA implements EmailService{
    private final EmailRepository emailRepository;
    private final EmailMapper emailMapper;

    public EmailServiceJPA(EmailRepository emailRepository, EmailMapper emailMapper) {
        this.emailRepository = emailRepository;
        this.emailMapper = emailMapper;
    }

    @Override
    public List<EmailDto> findAllEmails() {
        List<Email> emails = (List<Email>) emailRepository.findAll();
        return emails.stream()
                .map(emailMapper::emailToEmailDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmailDto addEmail(EmailDto emailDto) {
        User user = getCurrentUser();
        Email email = emailMapper.emailDtoToEmail(emailDto);
        email.setUser(user);
        Email savedEmail = emailRepository.save(email);
        return emailMapper.emailToEmailDto(savedEmail);
    }


    @Override
    @Transactional
    public void removeEmail(String email) {
        Long currentUserId = getCurrentUser().getId();

        boolean exists = emailRepository.existsByEmailAndUserId(email, currentUserId);
        if (!exists) {
            throw new NotFoundException("Email not found: " + email);
        }

        long emailCount = emailRepository.countByUserId(currentUserId);
        if (emailCount <= 1) {
            throw new IllegalStateException("Cannot delete the last email associated with the user.");
        }

        emailRepository.deleteByEmail(email);
    }


    @Override
    public Optional<EmailDto> changeEmail(ChangeEmailDto emailDto) {
        String oldEmail = emailDto.getOldEmail();
        String newEmail = emailDto.getNewEmail();
        Long currentUserId = getCurrentUser().getId();

        Email email = emailRepository.findByEmailAndUserId(oldEmail, currentUserId)
                .orElseThrow(() -> new NotFoundException("Email not found: " + oldEmail));

        email.setEmail(newEmail);
        Email changedEmail = emailRepository.save(email);

        return Optional.ofNullable(emailMapper.emailToEmailDto(changedEmail));
    }


    @Override
    public Optional<EmailDto> getEmail(String email) {
        return emailRepository.findByEmail(email)
                .map(emailMapper::emailToEmailDto)
                .or(Optional::empty);
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.user();
        } else {
            return null;
        }
    }

}
