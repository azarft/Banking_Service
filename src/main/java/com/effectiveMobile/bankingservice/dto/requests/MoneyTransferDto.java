package com.effectiveMobile.bankingservice.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransferDto {
    private Long recipientId;
    private BigDecimal amount;
}
