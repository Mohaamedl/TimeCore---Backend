package com.odin.Service;

import com.odin.Model.User;
import com.odin.Model.VerificationCode;
import com.odin.Repository.VerificationCodeRepository;
import com.odin.domain.VerificationType;
import com.odin.util.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService{

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;



    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {

        VerificationCode vc = new VerificationCode();
        vc.setOtp(OtpUtils.generateOtp());
        vc.setVerificationType(verificationType);
        vc.setUser(user);
        return verificationCodeRepository.save(vc);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) throws Exception {
        Optional<VerificationCode> vc = verificationCodeRepository.findById(id);
        if(vc.isPresent()){
            return vc.get();
        }
        throw new Exception("Verification code not found");

    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) {
        return verificationCodeRepository.findByUserId(userId);
    }

    @Override
    public void deleteVerificationCode(VerificationCode verificationCode) {
        verificationCodeRepository.delete(verificationCode);

    }
}
