package com.effectiveMobile.bankingservice.dto.entityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberDto {
    private Long numberId;
    private String phoneNumber;
    private Long userId;
}
