package com.odin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odin.domain.VerificationType;
import com.odin.dto.PasswordUpdateRequest;
import com.odin.model.User;
import com.odin.model.VerificationCode;
import com.odin.service.EmailService;
import com.odin.service.UserService;
import com.odin.service.VerificationCodeService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String jwt;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String jwt)  {
        try {
            User user = userService.findUserByJwt(jwt);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String jwt,
                                         @RequestBody User updatedUser) {
        try {
            User user = userService.findUserByJwt(jwt);
            user.setFullname(updatedUser.getFullname());
            user.setEmail(updatedUser.getEmail());
            User saved = userService.updateProfile(user);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/profile/password")
    public ResponseEntity<?> updatePassword(@RequestHeader("Authorization") String jwt,
                                          @RequestBody PasswordUpdateRequest request) {
        try {
            User user = userService.findUserByJwt(jwt);
            userService.updatePassword(user, request.getCurrentPassword(), 
                                    request.getNewPassword());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
