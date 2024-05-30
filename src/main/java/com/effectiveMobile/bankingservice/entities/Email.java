package com.effectiveMobile.bankingservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    @Id
    @GeneratedValue
    private Long emailId;

    @Column(unique = true, nullable = false)
    private String email;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
