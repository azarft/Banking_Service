package com.effectiveMobile.bankingservice.dto.entityDto;

import com.effectiveMobile.bankingservice.entities.User;
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

    private String fullName;

    private LocalDate dateOfBirth;

    public UserDto(User user) {
        this.login = user.getLogin();
        this.fullName = user.getFullName();
        this.id = user.getId();
        this.dateOfBirth = user.getDateOfBirth();
    }
}
