package com.effectiveMobile.bankingservice.repositories;

import com.effectiveMobile.bankingservice.entities.Email;
import com.effectiveMobile.bankingservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {
    Optional<Email> findByUserId(Long user_id);
    void deleteByUserIdAndEmailId(Long user_id, Long email_id);
    void deleteByEmail(String email);
    boolean existsByEmailAndUserId(String email, Long user_id);
    Optional<Email> findByEmailAndUserId(String email, Long user_id);
    Optional<Email> findByEmail(String email);
    long countByUserId(Long userId);
}
