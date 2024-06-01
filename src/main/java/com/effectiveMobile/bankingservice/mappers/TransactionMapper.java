package com.effectiveMobile.bankingservice.mappers;

import com.effectiveMobile.bankingservice.dto.entityDto.TransactionDto;
import com.effectiveMobile.bankingservice.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransactionMapper {
    @Mapping(source = "senderId", target = "sender.id")
    @Mapping(source = "recipientId", target = "recipient.id")
    Transaction transactionDtoToTransaction(TransactionDto dto);
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "recipient.id", target = "recipientId")
    TransactionDto transactionToTransactionDto(Transaction transaction);
}
