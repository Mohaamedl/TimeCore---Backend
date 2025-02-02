package com.odin.service;

import com.odin.domain.VerificationType;
import com.odin.model.User;

public interface UserService {
    public User findUserByJwt(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public User findUserById(Long userId) throws Exception;

    public User enableTwoFactorAuth(VerificationType verificationType, String sendTo,  User user);
    public User updatePassword(User user, String password);
    User updatePassword(User user, String currentPassword, String newPassword);

    public User updateProfile(User user);
}
