package com.effectiveMobile.bankingservice.dto.entityDto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    private Long emailId;
    private String email;
    private Long userId;
}
