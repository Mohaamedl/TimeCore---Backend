package com.odin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.odin.model.TwoFactorOTP;
import com.odin.model.User;
import com.odin.repository.TwoFactorOtpRepository;

/**
 * Implementation of the TwoFactorOTPService interface.
 */
@Service
public class TwoFactorOTPServiceImpl implements TwoFactorOTPService {

    @Autowired
    private TwoFactorOtpRepository twoFactorOtpRepository;

    /**
     * Default constructor.
     */
    public TwoFactorOTPServiceImpl() {
    }

    /**
     * Creates a new TwoFactorOTP.
     *
     * @param user The user.
     * @param otp The OTP.
     * @param jwt The JWT.
     * @return The created TwoFactorOTP.
     */
    @Override
    public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {
        TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
        twoFactorOTP.setUser(user);
        twoFactorOTP.setOtp(otp);
        twoFactorOTP.setJwt(jwt);
        return twoFactorOtpRepository.save(twoFactorOTP);
    }

    /**
     * Finds a TwoFactorOTP by user ID.
     *
     * @param userId The ID of the user.
     * @return The TwoFactorOTP, or null if not found.
     */
    @Override
    public TwoFactorOTP findByUser(Long userId) {
        return twoFactorOtpRepository.findByUserId(userId);
    }

    /**
     * Finds a TwoFactorOTP by ID.
     *
     * @param id The ID of the TwoFactorOTP.
     * @return The TwoFactorOTP, or null if not found.
     */
    @Override
    public TwoFactorOTP findById(String id) {
        return twoFactorOtpRepository.findById(id).orElse(null);
    }

    /**
     * Deletes a TwoFactorOTP.
     *
     * @param twoFactorOTP The TwoFactorOTP to delete.
     */
    @Override
    public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP) {
        twoFactorOtpRepository.delete(twoFactorOTP);
    }

    /**
     * Verifies a TwoFactorOTP.
     *
     * @param twoFactorOTP The TwoFactorOTP to verify.
     * @param otp The OTP to verify against.
     * @return True if the OTP is valid, false otherwise.
     */
    @Override
    public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp) {
        return twoFactorOTP.getOtp().equals(otp);
    }
}
