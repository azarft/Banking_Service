package com.effectiveMobile.bankingservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be positive or zero")
    @Column(nullable = false)
    private BigDecimal balance;

    @DecimalMin(value = "0.0", inclusive = true, message = "Initial deposit must be positive or zero")
    @Column(nullable = false)
    private BigDecimal initialDeposit;

    @Builder.Default
    private double maxBalancePercentage = 2.07;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}

