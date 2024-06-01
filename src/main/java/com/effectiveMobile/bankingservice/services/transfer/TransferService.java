package com.effectiveMobile.bankingservice.services.transfer;

import com.effectiveMobile.bankingservice.dto.entityDto.TransactionDto;
import com.effectiveMobile.bankingservice.dto.requests.MoneyTransferDto;

import java.util.List;

public interface TransferService {
    TransactionDto transferMoney(MoneyTransferDto dto);
    List<TransactionDto> findAllTransactions();
}
