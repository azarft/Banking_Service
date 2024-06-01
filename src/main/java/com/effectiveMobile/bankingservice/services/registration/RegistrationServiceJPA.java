package com.effectiveMobile.bankingservice.services.registration;

import com.effectiveMobile.bankingservice.dto.requests.RegistrationDto;
import com.effectiveMobile.bankingservice.dto.entityDto.UserDto;
import com.effectiveMobile.bankingservice.entities.Account;
import com.effectiveMobile.bankingservice.entities.Email;
import com.effectiveMobile.bankingservice.entities.PhoneNumber;
import com.effectiveMobile.bankingservice.entities.User;
import com.effectiveMobile.bankingservice.mappers.UserMapper;
import com.effectiveMobile.bankingservice.repositories.AccountRepository;
import com.effectiveMobile.bankingservice.repositories.EmailRepository;
import com.effectiveMobile.bankingservice.repositories.PhoneNumberRepository;
import com.effectiveMobile.bankingservice.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RegistrationServiceJPA implements RegistrationService{
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final EmailRepository emailRepository;
    private final AccountRepository accountRepository;

    public RegistrationServiceJPA(UserMapper userMapper, PasswordEncoder passwordEncoder, UserRepository userRepository, PhoneNumberRepository phoneNumberRepository, EmailRepository emailRepository, AccountRepository accountRepository) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.phoneNumberRepository = phoneNumberRepository;
        this.emailRepository = emailRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDto registerUser(RegistrationDto dto) {
        User user = User.builder()
                .login(dto.getLogin())
                .password(passwordEncoder.encode(dto.getPassword()))
                .fullName(dto.getFullName())
                .dateOfBirth(dto.getBirthday())
                .build();
        User savedUser = userRepository.save(user);

        Set<Email> emails = new HashSet<>();
        Set<PhoneNumber> phoneNumbers = new HashSet<>();
        Account account = Account.builder()
                .initialDeposit(dto.getInitialDeposit())
                .balance(dto.getInitialDeposit())
                .user(savedUser)
                .build();
        Email email = Email.builder()
                .email(dto.getEmail())
                .user(savedUser)
                .build();
        PhoneNumber phoneNumber = PhoneNumber.builder()
                .phoneNumber(dto.getPhoneNumber())
                .user(savedUser)
                .build();
        emails.add(email);
        phoneNumbers.add(phoneNumber);

        emailRepository.save(email);
        phoneNumberRepository.save(phoneNumber);
        accountRepository.save(account);
        return userMapper.userToUserDto(savedUser);
    }
}
