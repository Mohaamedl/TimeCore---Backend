package com.odin.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.odin.config.JwtProvider;
import com.odin.domain.VerificationType;
import com.odin.model.TwoFactorAuth;
import com.odin.model.User;
import com.odin.repository.UserRepository;

/**
 * Implementation of the UserService interface.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Constructs a new UserServiceImpl with the provided UserRepository.
     *
     * @param userRepository the repository for User entities
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found with email : " + email);
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found with email : " + email);
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
        {
            throw new Exception("User not found with id : " + userId);
        }
        return user.get();
    }


    @Override
    public User enableTwoFactorAuth(VerificationType verificationType, String sendTo, User user) {
        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSendTo(verificationType);
        user.setTwoFactorAuth(twoFactorAuth);
        return userRepository.save(user);
    }

    @Override
    public User updateProfile(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            throw new RuntimeException("Email already in use");
        }
        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User user, String currentPassword, String newPassword) {
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User user, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
