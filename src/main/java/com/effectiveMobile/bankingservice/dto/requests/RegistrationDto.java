package com.effectiveMobile.bankingservice.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {
    private String login;
    private String password;
    private BigDecimal initialDeposit;
    private String phoneNumber;
    private String email;
    private String fullName;
    private LocalDate birthday;
}
