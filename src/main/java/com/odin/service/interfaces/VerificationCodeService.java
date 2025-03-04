package com.odin.service.interfaces;

import com.odin.domain.VerificationType;
import com.odin.model.User;
import com.odin.model.VerificationCode;

/**
 * Service interface for managing verification codes.
 */
public interface VerificationCodeService {

    /**
     * Sends a verification code to the user.
     *
     * @param user             the user to send the verification code to
     * @param verificationType the type of verification
     * @return the generated verification code
     */
    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    /**
     * Retrieves a verification code by its ID.
     *
     * @param id the ID of the verification code
     * @return the verification code
     * @throws Exception if the verification code is not found
     */
    VerificationCode getVerificationCodeById(Long id) throws Exception;

    /**
     * Retrieves a verification code by user ID.
     *
     * @param userId the ID of the user
     * @return the verification code
     */
    VerificationCode getVerificationCodeByUser(Long userId);

    /**
     * Processes the verification queue.
     */
    public void processVerificationQueue();

    /**
     * Deletes a verification code.
     *
     * @param verificationCode the verification code to delete
     */
    void deleteVerificationCode(VerificationCode verificationCode);
}
