package com.odin.service;

import com.odin.model.User;
import com.odin.domain.VerificationType;

public interface UserService {
    public User findUserByJwt(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public User findUserById(Long userId) throws Exception;

    public User enableTwoFactorAuth(VerificationType verificationType, String sendTo,  User user);
    public User updatePassword(User user, String password);
}
