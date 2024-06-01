package com.effectiveMobile.bankingservice.repositories;

import com.effectiveMobile.bankingservice.entities.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber,Long> {
        Optional<PhoneNumber> findByUserId(Long user_id);
        void deleteByUserIdAndNumberId(Long user_id, Long number_id);
        boolean existsByPhoneNumberAndUserId(String phoneNumber, Long user_id);
        long countByUserId(Long userId);
        void deleteByPhoneNumber(String phoneNumber);
        Optional<PhoneNumber> findByPhoneNumberAndUserId(String phoneNumber, Long user_id);
        Optional<PhoneNumber> findByPhoneNumber(String phoneNumber);
}
