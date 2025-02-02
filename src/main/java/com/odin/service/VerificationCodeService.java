package com.odin.Service;

import com.odin.Model.User;
import com.odin.Model.VerificationCode;
import com.odin.domain.VerificationType;

public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user, VerificationType verificationType);
    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userId);

    //Boolean verifyVerificationCode(VerificationCode verificationCode, String otp);

    void deleteVerificationCode(VerificationCode verificationCode);
}
