package com.effectiveMobile.bankingservice.services.user;

import com.effectiveMobile.bankingservice.dto.auth.CustomUserDetails;
import com.effectiveMobile.bankingservice.dto.entityDto.UserDto;
import com.effectiveMobile.bankingservice.entities.User;
import com.effectiveMobile.bankingservice.exceptions.NotFoundException;
import com.effectiveMobile.bankingservice.mappers.UserMapper;
import com.effectiveMobile.bankingservice.repositories.UserRepository;
import com.effectiveMobile.bankingservice.specifications.UserSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class UserServiceJPA implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceJPA(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findUserByID(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        return Optional.of(userMapper.userToUserDto(user));
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getLogin() + LocalDate.now().getYear()));
        User savedUser = userRepository.save(user);
        return userMapper.userToUserDto(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.user();
        } else {
            return null;
        }
    }

    @Override
    public Page<UserDto> searchUsers(LocalDate dateOfBirth, String phoneNumber, String fullName, String email, Pageable pageable) {
        Specification<User> spec = Specification.where(null);

        if (dateOfBirth != null) {
            spec = spec.and(UserSpecification.hasDateOfBirthAfter(dateOfBirth));
        }

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            spec = spec.and(UserSpecification.hasPhoneNumber(phoneNumber));
        }

        if (fullName != null && !fullName.isEmpty()) {
            spec = spec.and(UserSpecification.hasFullNameLike(fullName));
        }

        if (email != null && !email.isEmpty()) {
            spec = spec.and(UserSpecification.hasEmail(email));
        }

        Page<User> userPage = userRepository.findAll(spec, pageable);
        return userPage.map(userMapper::userToUserDto);
    }


}
