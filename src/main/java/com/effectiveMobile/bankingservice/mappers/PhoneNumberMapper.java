package com.effectiveMobile.bankingservice.mappers;

import com.effectiveMobile.bankingservice.dto.PhoneNumberDto;
import com.effectiveMobile.bankingservice.entities.PhoneNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PhoneNumberMapper {
    @Mapping(source = "userId", target = "user.id")
    PhoneNumber phoneNumberDtoToPhoneNumber(PhoneNumberDto dto);
    @Mapping(source = "user.id", target = "userId")
    PhoneNumberDto phoneNumberToPhoneNumberDto(PhoneNumber phoneNumber);
}
