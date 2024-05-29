package com.effectiveMobile.bankingservice.repositories;

import com.effectiveMobile.bankingservice.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
