package com.effectiveMobile.bankingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDto {
    private Long id;

    private BigDecimal balance;

    private BigDecimal initialDeposit;

    private Long userId;
}
