package com.effectiveMobile.bankingservice.repositories;

import com.effectiveMobile.bankingservice.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long id);
    Optional<Account> findByUserId(Long user_id);
}
