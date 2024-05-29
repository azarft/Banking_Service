package com.effectiveMobile.bankingservice.repositories;

import com.effectiveMobile.bankingservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
}