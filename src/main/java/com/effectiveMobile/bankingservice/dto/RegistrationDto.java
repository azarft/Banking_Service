package com.effectiveMobile.bankingservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
}
