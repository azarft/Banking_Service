package com.effectiveMobile.bankingservice.repositories;

import com.effectiveMobile.bankingservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User>{
    Optional<User> findByLogin(String login);
    Optional<User> findById(Long id);
}
