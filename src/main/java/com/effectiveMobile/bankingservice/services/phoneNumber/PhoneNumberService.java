package com.effectiveMobile.bankingservice.services.phoneNumber;

import com.effectiveMobile.bankingservice.dto.requests.ChangePhoneNumberDto;
import com.effectiveMobile.bankingservice.dto.entityDto.PhoneNumberDto;

import java.util.List;
import java.util.Optional;

public interface PhoneNumberService {
    List<PhoneNumberDto> findAllPhoneNumbers();
    PhoneNumberDto addPhoneNumber(PhoneNumberDto phoneNumberDto);
    void removePhoneNumber(String phoneNumber);
    Optional<PhoneNumberDto> changePhoneNumber(ChangePhoneNumberDto phoneNumberDto);
    Optional<PhoneNumberDto> getPhoneNumber(String phoneNumber);
}

