package com.effectiveMobile.bankingservice.repositories;

import com.effectiveMobile.bankingservice.entities.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber,Long> {
        Optional<PhoneNumber> findByUserId(Long user_id);
        void deleteByUserIdAndNumberId(Long user_id, Long number_id);
}
