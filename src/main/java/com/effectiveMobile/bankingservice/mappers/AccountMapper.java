package com.effectiveMobile.bankingservice.mappers;

import com.effectiveMobile.bankingservice.dto.AccountDto;
import com.effectiveMobile.bankingservice.entities.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AccountMapper {
    @Mapping(source = "user.id", target = "userId")
    AccountDto accountToAccountDto(Account account);
    @Mapping(source = "userId", target = "user.id")
    Account accountDtoToAccount(AccountDto dto);
}
