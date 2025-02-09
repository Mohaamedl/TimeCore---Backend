package com.odin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odin.model.VerificationCode;

/**
 * Repository for managing {@link VerificationCode} entities.
 */
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    /**
     * Finds a verification code by user ID.
     *
     * @param userId the ID of the user
     * @return the verification code, or null if not found
     */
    public VerificationCode findByUserId(Long userId);
}
