package com.odin.dto;

import lombok.Data;

/**
 * Data transfer object for password update requests.
 */
@Data
public class PasswordUpdateRequest {
    private String currentPassword;
    private String newPassword;

    /**
     * Default constructor.
     */
    public PasswordUpdateRequest() {
    }

    /**
     * Gets the current password.
     *
     * @return The current password.
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * Gets the new password.
     *
     * @return The new password.
     */
    public String getNewPassword() {
        return newPassword;
    }
}