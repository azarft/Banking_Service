package com.effectiveMobile.bankingservice.services.user;

import com.effectiveMobile.bankingservice.dto.entityDto.UserDto;
import com.effectiveMobile.bankingservice.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> findAllUsers();

    Optional<UserDto> findUserByID(Long id);

    UserDto saveUser(UserDto userDto);

    void deleteUser(Long id);
    User getCurrentUser();
    public Page<UserDto> searchUsers(LocalDate dateOfBirth, String phoneNumber, String fullName, String email, Pageable pageable);
}
