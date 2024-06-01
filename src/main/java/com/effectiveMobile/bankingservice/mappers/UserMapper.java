package com.effectiveMobile.bankingservice.mappers;

import com.effectiveMobile.bankingservice.dto.entityDto.UserDto;
import com.effectiveMobile.bankingservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User userDtoToUser(UserDto dto);
    UserDto userToUserDto(User user);
}
