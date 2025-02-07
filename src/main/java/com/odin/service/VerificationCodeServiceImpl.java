package com.odin.service;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.odin.domain.VerificationType;
import com.odin.model.User;
import com.odin.model.VerificationCode;
import com.odin.repository.VerificationCodeRepository;
import com.odin.util.OtpUtils;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    private final Queue<VerificationCode> pendingVerifications = new ConcurrentLinkedQueue<>();

    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
        VerificationCode vc = new VerificationCode();
        vc.setOtp(OtpUtils.generateOtp());
        vc.setVerificationType(verificationType);
        vc.setUser(user);

        // Add to pending queue
        pendingVerifications.offer(vc);

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

    @Override 
    public void processVerificationQueue() {
        while (!pendingVerifications.isEmpty()) {
            VerificationCode vc = pendingVerifications.poll();
           
            try {
               
                verificationCodeRepository.save(vc);
            } catch (Exception e) {
               
                pendingVerifications.offer(vc); 
            }
        }
    }
}
