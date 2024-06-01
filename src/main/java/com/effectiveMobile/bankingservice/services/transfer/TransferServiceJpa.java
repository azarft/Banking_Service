package com.effectiveMobile.bankingservice.services.transfer;

import com.effectiveMobile.bankingservice.dto.auth.CustomUserDetails;
import com.effectiveMobile.bankingservice.dto.entityDto.TransactionDto;
import com.effectiveMobile.bankingservice.dto.requests.MoneyTransferDto;
import com.effectiveMobile.bankingservice.entities.Account;
import com.effectiveMobile.bankingservice.entities.Transaction;
import com.effectiveMobile.bankingservice.entities.User;
import com.effectiveMobile.bankingservice.exceptions.InsufficientFundsException;
import com.effectiveMobile.bankingservice.exceptions.NotFoundException;
import com.effectiveMobile.bankingservice.mappers.TransactionMapper;
import com.effectiveMobile.bankingservice.repositories.AccountRepository;
import com.effectiveMobile.bankingservice.repositories.TransactionRepository;
import com.effectiveMobile.bankingservice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransferServiceJpa implements TransferService{
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransferServiceJpa(UserRepository userRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Transactional
    @Override
    public TransactionDto transferMoney(MoneyTransferDto dto) {
        User sender = userRepository.findByLogin(getCurrentUser().getLogin())
                .orElseThrow(() -> new NotFoundException("Sender not found"));

        User recipient = userRepository.findById(dto.getRecipientId())
                .orElseThrow(() -> new NotFoundException("Recipient not found"));

        if (sender.getId().equals(recipient.getId())) {
            throw new IllegalArgumentException("Sender and recipient cannot be the same");
        }

        Account senderAccount = accountRepository.findByUserId(sender.getId())
                .orElseThrow(() -> new NotFoundException("Sender's Account not found"));
        Account recipientAccount = accountRepository.findByUserId(recipient.getId())
                .orElseThrow(() -> new NotFoundException("Recipient's Account not found"));

        BigDecimal amount = dto.getAmount();
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Sender has insufficient funds");
        }

        // Lock the accounts to prevent concurrent modifications
        synchronized (getAccountLock(senderAccount.getId())) {
            synchronized (getAccountLock(recipientAccount.getId())) {
                senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
                recipientAccount.setBalance(recipientAccount.getBalance().add(amount));

                accountRepository.saveAll(Set.of(senderAccount, recipientAccount));
            }
        }

        Transaction transaction = Transaction.builder()
                .sender(sender)
                .recipient(recipient)
                .amount(amount)
                .build();
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.transactionToTransactionDto(savedTransaction);
    }


    private Object getAccountLock(Long accountId) {
        // You can use a more sophisticated lock management here
        return accountId.toString().intern();
    }

    @Override
    public List<TransactionDto> findAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(transactionMapper::transactionToTransactionDto)
                .collect(Collectors.toList());
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.user();
        } else {
            return null;
        }
    }
}
