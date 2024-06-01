package com.effectiveMobile.bankingservice.dto.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeEmailDto {
    private String oldEmail;
    private String newEmail;
}
