package com.effectiveMobile.bankingservice.repositories;

import com.effectiveMobile.bankingservice.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {
    Optional<Email> findByUserId(Long user_id);
    void deleteByUserIdAndEmailId(Long user_id, Long email_id);
}
