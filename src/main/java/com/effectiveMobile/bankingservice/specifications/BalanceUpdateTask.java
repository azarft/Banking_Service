package com.effectiveMobile.bankingservice.specifications;

import com.effectiveMobile.bankingservice.services.account.BalanceUpdateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BalanceUpdateTask {
    private final BalanceUpdateService balanceUpdateService;

    public BalanceUpdateTask(BalanceUpdateService balanceUpdateService) {
        this.balanceUpdateService = balanceUpdateService;
    }

    @Scheduled(fixedRate = 60000) // Run every minute
    public void updateBalances() {
        try {
            balanceUpdateService.updateBalances();
        } catch (Exception e) {
            // Handle any exceptions that may occur during the balance update
            // For example, log the error or send a notification
            e.printStackTrace();
        }
    }
}