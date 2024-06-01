package com.effectiveMobile.bankingservice.services.account;

import com.effectiveMobile.bankingservice.entities.Account;
import com.effectiveMobile.bankingservice.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BalanceUpdateService {
    private final AccountRepository accountRepository;

    public BalanceUpdateService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void updateBalances() {
        List<Account> accounts = accountRepository.findAll();
        for (Account account : accounts) {
            updateBalance(account);
        }
    }

    private void updateBalance(Account account) {
        Account updatedAccount = accountRepository.findById(account.getId()).orElse(account);
        BigDecimal newBalance = updatedAccount.getBalance().multiply(BigDecimal.valueOf(1.05)); // Increase by 5%
        BigDecimal maxBalance = updatedAccount.getInitialDeposit().multiply(BigDecimal.valueOf(updatedAccount.getMaxBalancePercentage()));
        if (newBalance.compareTo(maxBalance) > 0) {
                updatedAccount.setBalance(maxBalance);
        } else {
            updatedAccount.setBalance(newBalance);
        }
        accountRepository.save(updatedAccount);
    }
}