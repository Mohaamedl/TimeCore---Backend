package com.odin.service;

import com.odin.domain.VerificationType;
import com.odin.model.User;
import com.odin.model.VerificationCode;

public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user, VerificationType verificationType);
    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userId);

    //Boolean verifyVerificationCode(VerificationCode verificationCode, String otp);
    public void processVerificationQueue();
    void deleteVerificationCode(VerificationCode verificationCode);
}
