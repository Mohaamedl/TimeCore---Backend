package com.odin.Service;

import com.odin.Model.User;
import com.odin.domain.VerificationType;

public interface UserService {
    public User findUserByJwt(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public User findUserById(Long userId) throws Exception;

    public User enableTwoFactorAuth(VerificationType verificationType, String sendTo,  User user);
    public User updatePassword(User user, String password);
}
