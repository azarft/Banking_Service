package com.effectiveMobile.bankingservice.controllers;

import com.effectiveMobile.bankingservice.dto.entityDto.TransactionDto;
import com.effectiveMobile.bankingservice.dto.requests.MoneyTransferDto;
import com.effectiveMobile.bankingservice.services.transfer.TransferServiceJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transfers")
@RequiredArgsConstructor
public class TransferController {
    private final TransferServiceJpa transferService;

    @PostMapping("/transfer")
    @io.swagger.v3.oas.annotations.Operation
    public TransactionDto transferMoney (@RequestBody MoneyTransferDto dto){
        return transferService.transferMoney(dto);
    }
    @GetMapping
    @io.swagger.v3.oas.annotations.Operation
    public List<TransactionDto> getAllTransactions (){
        return transferService.findAllTransactions();
    }
}
