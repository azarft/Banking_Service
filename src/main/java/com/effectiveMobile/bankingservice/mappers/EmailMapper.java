package com.effectiveMobile.bankingservice.mappers;

import com.effectiveMobile.bankingservice.dto.entityDto.EmailDto;
import com.effectiveMobile.bankingservice.entities.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EmailMapper {
    @Mapping(source = "userId", target = "user.id")
    Email emailDtoToEmail(EmailDto dto);
    @Mapping(source = "user.id", target = "userId")
    EmailDto emailToEmailDto(Email email);
}
