package com.odin.service.interfaces;

import com.odin.model.TwoFactorOTP;
import com.odin.model.User;

/**
 * Service interface for managing TwoFactorOTP entities.
 */
public interface TwoFactorOTPService {

    /**
     * Creates a new TwoFactorOTP.
     *
     * @param user The user.
     * @param otp The OTP.
     * @param jwt The JWT.
     * @return The created TwoFactorOTP.
     */
    TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);

    /**
     * Finds a TwoFactorOTP by user ID.
     *
     * @param userId The ID of the user.
     * @return The TwoFactorOTP, or null if not found.
     */
    TwoFactorOTP findByUser(Long userId);

    /**
     * Finds a TwoFactorOTP by ID.
     *
     * @param id The ID of the TwoFactorOTP.
     * @return The TwoFactorOTP, or null if not found.
     */
    TwoFactorOTP findById(String id);

    /**
     * Deletes a TwoFactorOTP.
     *
     * @param twoFactorOTP The TwoFactorOTP to delete.
     */
    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);

    /**
     * Verifies a TwoFactorOTP.
     *
     * @param twoFactorOTP The TwoFactorOTP to verify.
     * @param otp The OTP to verify against.
     * @return True if the OTP is valid, false otherwise.
     */
    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp);
}
