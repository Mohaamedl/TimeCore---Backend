package com.odin.repository;

import com.odin.model.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for managing TwoFactorOTP entities.
 */
public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String> {

    /**
     * Finds a TwoFactorOTP by user ID.
     *
     * @param userId The ID of the user.
     * @return The TwoFactorOTP, or null if not found.
     */
    TwoFactorOTP findByUserId(Long userId);
}
