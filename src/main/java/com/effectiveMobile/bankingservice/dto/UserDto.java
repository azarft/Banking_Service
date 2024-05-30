package com.effectiveMobile.bankingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private Long id;

    private String login;

    private String email;

    private String phoneNumber;

    private String fullName;

    private LocalDate dateOfBirth;
}
