package com.odin.controller;


import com.odin.model.User;
import com.odin.model.VerificationCode;
import com.odin.service.EmailService;
import com.odin.service.UserService;
import com.odin.service.VerificationCodeService;
import com.odin.domain.VerificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private EmailService emailService;


    private String jwt;


    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
       User user =  userService.findUserByJwt(jwt);

       return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(
            @RequestHeader("Authorization") String jwt,
            @PathVariable VerificationType verificationType) throws Exception
    {

        User user =  userService.findUserByJwt(jwt);

        VerificationCode vc = verificationCodeService
                .getVerificationCodeByUser(user.getId());

        if (vc == null){
            vc = verificationCodeService.sendVerificationCode(user, verificationType);
        }
        if (verificationType.equals(VerificationType.EMAIL))
        {
            emailService.sendVerificationOtpEmail(user.getEmail(), vc.getOtp());
        }



        return new ResponseEntity<>("Verification code sent successfully", HttpStatus.OK);
    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuth(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String otp
            ) throws Exception {
        User user =  userService.findUserByJwt(jwt);
        VerificationCode vc = verificationCodeService.getVerificationCodeByUser(user.getId());

        String sendTo = vc
                .getVerificationType()
                .equals(VerificationType.EMAIL) ? vc.getEmail() : vc.getMobile();

        boolean isVerified = vc.getOtp().equals(otp);

        if (isVerified){
            User updatedUser = userService.enableTwoFactorAuth(
                    vc.getVerificationType(),
                    sendTo,
                    user);
            verificationCodeService.deleteVerificationCode(vc);

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        throw new Exception("Invalid OTP");
    }

}
