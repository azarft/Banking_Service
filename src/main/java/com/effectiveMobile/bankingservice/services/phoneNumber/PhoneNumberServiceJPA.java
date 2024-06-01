package com.effectiveMobile.bankingservice.services.phoneNumber;

import com.effectiveMobile.bankingservice.dto.requests.ChangePhoneNumberDto;
import com.effectiveMobile.bankingservice.dto.auth.CustomUserDetails;
import com.effectiveMobile.bankingservice.dto.entityDto.PhoneNumberDto;
import com.effectiveMobile.bankingservice.entities.PhoneNumber;
import com.effectiveMobile.bankingservice.entities.User;
import com.effectiveMobile.bankingservice.exceptions.NotFoundException;
import com.effectiveMobile.bankingservice.mappers.PhoneNumberMapper;
import com.effectiveMobile.bankingservice.repositories.PhoneNumberRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhoneNumberServiceJPA implements PhoneNumberService {
    private final PhoneNumberRepository phoneNumberRepository;
    private final PhoneNumberMapper phoneNumberMapper;

    public PhoneNumberServiceJPA(PhoneNumberRepository phoneNumberRepository, PhoneNumberMapper phoneNumberMapper) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.phoneNumberMapper = phoneNumberMapper;
    }

    @Override
    public List<PhoneNumberDto> findAllPhoneNumbers() {
        List<PhoneNumber> phoneNumbers = (List<PhoneNumber>) phoneNumberRepository.findAll();
        return phoneNumbers.stream()
                .map(phoneNumberMapper::phoneNumberToPhoneNumberDto)
                .collect(Collectors.toList());
    }

    @Override
    public PhoneNumberDto addPhoneNumber(PhoneNumberDto phoneNumberDto) {
        User user = getCurrentUser();
        PhoneNumber phoneNumber = phoneNumberMapper.phoneNumberDtoToPhoneNumber(phoneNumberDto);
        phoneNumber.setUser(user);
        PhoneNumber savedPhoneNumber = phoneNumberRepository.save(phoneNumber);
        return phoneNumberMapper.phoneNumberToPhoneNumberDto(savedPhoneNumber);
    }

    @Override
    @Transactional
    public void removePhoneNumber(String phoneNumber) {
        Long currentUserId = getCurrentUser().getId();

        boolean exists = phoneNumberRepository.existsByPhoneNumberAndUserId(phoneNumber, currentUserId);
        if (!exists) {
            throw new NotFoundException("Phone number not found: " + phoneNumber);
        }

        long phoneNumberCount = phoneNumberRepository.countByUserId(currentUserId);
        if (phoneNumberCount <= 1) {
            throw new IllegalStateException("Cannot delete the last phone number associated with the user.");
        }

        phoneNumberRepository.deleteByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<PhoneNumberDto> changePhoneNumber(ChangePhoneNumberDto phoneNumberDto) {
        String oldPhoneNumber = phoneNumberDto.getOldPhoneNumber();
        String newPhoneNumber = phoneNumberDto.getNewPhoneNumber();
        Long currentUserId = getCurrentUser().getId();

        PhoneNumber phoneNumber = phoneNumberRepository.findByPhoneNumberAndUserId(oldPhoneNumber, currentUserId)
                .orElseThrow(() -> new NotFoundException("Phone number not found: " + oldPhoneNumber));

        phoneNumber.setPhoneNumber(newPhoneNumber);
        PhoneNumber changedPhoneNumber = phoneNumberRepository.save(phoneNumber);

        return Optional.ofNullable(phoneNumberMapper.phoneNumberToPhoneNumberDto(changedPhoneNumber));
    }

    @Override
    public Optional<PhoneNumberDto> getPhoneNumber(String phoneNumber) {
        return phoneNumberRepository.findByPhoneNumber(phoneNumber)
                .map(phoneNumberMapper::phoneNumberToPhoneNumberDto)
                .or(Optional::empty);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.user();
        } else {
            return null;
        }
    }
}
