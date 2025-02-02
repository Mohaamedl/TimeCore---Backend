package com.odin.service;

import com.odin.model.TwoFactorOTP;
import com.odin.model.User;

public interface TwoFactorOTPService {
    TwoFactorOTP createTwoFactorOtp (User user, String otp, String jwt);


    TwoFactorOTP findByUser(Long userId);

    TwoFactorOTP findById(String id);

    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp);

    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);
}
