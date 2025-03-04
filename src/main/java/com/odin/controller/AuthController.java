package com.odin.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.odin.config.JwtProvider;
import com.odin.model.TwoFactorOTP;
import com.odin.model.User;
import com.odin.repository.UserRepository;
import com.odin.response.AuthResponse;
import com.odin.service.CustomUserDetailsService;
import com.odin.service.EmailService;
import com.odin.service.interfaces.TwoFactorOTPService;
import com.odin.util.OtpUtils;

/**
 * Controller handling authentication operations.
 * Provides endpoints for user registration, login, and 2FA verification.
 *
 * @author TimeCore Team  
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    /**
     * Default constructor
     */
    public AuthController() {
        // Default constructor
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TwoFactorOTPService twoFactorOTPService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;
 
    /**
     * Registers a new user in the system.
     *
     * @param user User details for registration
     * @return ResponseEntity with auth token and status
     * @throws Exception if email already exists
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {
        // Check if email exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new Exception("Email already registered");
        }

        // Create new user
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFullname(user.getFullname());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        newUser.setTwoFactorAuth(user.getTwoFactorAuth());

        User savedUser = userRepository.save(newUser);

        // Generate token
        Authentication auth = new UsernamePasswordAuthenticationToken(
                savedUser.getEmail(),
                savedUser.getPassword(),
                new ArrayList<>()
        );

        String token = JwtProvider.generateToken(auth);

        AuthResponse response = new AuthResponse();
        response.setJwt(token);
        response.setStatus(true);
        response.setMessage("Registration successful");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Authenticates a user and generates JWT token.
     *
     * @param user Login credentials containing email and password
     * @return ResponseEntity with auth token or 2FA challenge
     * @throws BadCredentialsException if credentials are invalid
     * @throws Exception if any other error occurs during authentication
     */ 
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();

        // Find user
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new BadCredentialsException("Invalid email/password");
        }

        // Verify password
        if (!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new BadCredentialsException("Invalid email/password");
        }

        // Create authentication
        Authentication auth = new UsernamePasswordAuthenticationToken(
                email,
                existingUser.getPassword(),
                new ArrayList<>()
        );

        String token = JwtProvider.generateToken(auth);

        // Check 2FA
        if (existingUser.getTwoFactorAuth().isEnabled()) {
            String otp = OtpUtils.generateOtp();

            // Delete old OTP if exists
            TwoFactorOTP oldOtp = twoFactorOTPService.findByUser(existingUser.getId());
            if (oldOtp != null) {
                twoFactorOTPService.deleteTwoFactorOtp(oldOtp);
            }

            // Create new OTP
            TwoFactorOTP newOtp = twoFactorOTPService.createTwoFactorOtp(existingUser, otp, token);
            emailService.sendVerificationOtpEmail(email, otp);

            AuthResponse response = new AuthResponse();
            response.setMessage("2FA Required");
            response.setStatus(true);
            response.setTwoFactorAuthEnabled(true);
            response.setSession(newOtp.getId());

            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }

        // Return normal response
        AuthResponse response = new AuthResponse();
        response.setJwt(token);
        response.setStatus(true);
        response.setMessage("Login successful");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }
        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());


    }

    /**
     * Verifies 2FA OTP code during sign in.
     * 
     * @param otp OTP code to verify
     * @param id Session ID for verification
     * @return ResponseEntity with auth token if verified
     * @throws BadCredentialsException if OTP is invalid
     */
    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySignInOtp(
            @PathVariable String otp,
            @RequestParam String id) {
        TwoFactorOTP twoFactorOTP = twoFactorOTPService.findById(id);

        if (twoFactorOTPService.verifyTwoFactorOtp(twoFactorOTP, otp)) {
            AuthResponse res = new AuthResponse();
            res.setMessage("Two factor authentication verified");
            res.setTwoFactorAuthEnabled(true);
            res.setStatus(true);
            res.setJwt(twoFactorOTP.getJwt());
            res.setSession(id);

            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        throw new BadCredentialsException("Invalid OTP");
    }


}
