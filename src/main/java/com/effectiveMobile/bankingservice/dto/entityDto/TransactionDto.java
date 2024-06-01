package com.effectiveMobile.bankingservice.dto.entityDto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal amount;

    private Long senderId;

    private Long recipientId;
}
