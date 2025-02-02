package com.odin.service;

import com.odin.model.User;
import com.odin.model.VerificationCode;
import com.odin.domain.VerificationType;

public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user, VerificationType verificationType);
    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userId);

    //Boolean verifyVerificationCode(VerificationCode verificationCode, String otp);

    void deleteVerificationCode(VerificationCode verificationCode);
}
