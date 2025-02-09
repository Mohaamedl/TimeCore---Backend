package com.odin.service;

import com.odin.domain.VerificationType;
import com.odin.model.User;

/**
 * Service interface for managing user operations.
 * Handles user authentication, profile management and security features.
 */
public interface UserService {
    /**
     * Finds a user by their JWT token
     * @param jwt JWT token string
     * @return User associated with the token
     * @throws Exception if user not found or token invalid
     */
    public User findUserByJwt(String jwt) throws Exception;

    /**
     * Finds a user by their email address
     * @param email Email address to search for
     * @return User with the specified email
     * @throws Exception if user not found
     */
    public User findUserByEmail(String email) throws Exception;

    /**
     * Finds a user by their ID
     * @param userId User ID to search for
     * @return User with the specified ID
     * @throws Exception if user not found
     */
    public User findUserById(Long userId) throws Exception;

    /**
     * Enables two-factor authentication for a user
     * @param verificationType Type of verification (EMAIL/MOBILE)
     * @param sendTo Contact information for sending verification codes
     * @param user User to enable 2FA for
     * @return Updated user object
     */
    public User enableTwoFactorAuth(VerificationType verificationType, String sendTo, User user);

    /**
     * Updates a user's password
     * @param user User to update
     * @param password New password
     * @return Updated user object
     */
    public User updatePassword(User user, String password);

    /**
     * Updates a user's password with verification
     * @param user User to update
     * @param currentPassword Current password for verification
     * @param newPassword New password to set
     * @return Updated user object
     */
    User updatePassword(User user, String currentPassword, String newPassword);

    /**
     * Updates a user's profile information
     * @param user User object containing updated information
     * @return Updated user object
     */
    public User updateProfile(User user);
}
