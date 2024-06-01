package com.effectiveMobile.bankingservice.specifications;

import com.effectiveMobile.bankingservice.entities.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class UserSpecification {

    public static Specification<User> hasDateOfBirthAfter(LocalDate dateOfBirth) {
        return (root, query, criteriaBuilder) -> {
            if (dateOfBirth == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThan(root.get("dateOfBirth"), dateOfBirth);
        };
    }

    public static Specification<User> hasPhoneNumber(String phoneNumber) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(phoneNumber)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.join("phoneNumbers").get("phoneNumber"), phoneNumber);
        };
    }

    public static Specification<User> hasFullNameLike(String fullName) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(fullName)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("fullName"), fullName + "%");
        };
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(email)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.join("emails").get("email"), email);
        };
    }
}
