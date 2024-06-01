package com.effectiveMobile.bankingservice.services.registration;

import com.effectiveMobile.bankingservice.dto.requests.RegistrationDto;
import com.effectiveMobile.bankingservice.dto.entityDto.UserDto;

public interface RegistrationService {
    UserDto registerUser(RegistrationDto dto);
}
