package com.odin.model;

import com.odin.domain.VerificationType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

/**
 * Represents two-factor authentication settings for a user.
 * This is an embeddable class used within User entity.
 */
@Data
public class TwoFactorAuth {

    private boolean enabled = false;

    @Enumerated(EnumType.STRING)
    private VerificationType sendTo = VerificationType.EMAIL;

    /**
     * Default constructor.
     */
    public TwoFactorAuth() {
        enabled = false;
        sendTo = VerificationType.EMAIL;
    }

    /**
     * Checks if two-factor authentication is enabled.
     *
     * @return True if enabled, false otherwise.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether two-factor authentication is enabled.
     *
     * @param enabled True to enable, false to disable.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets the send-to destination for two-factor authentication.
     *
     * @return The send-to destination.
     */
    public VerificationType getSendTo() {
        return sendTo;
    }

    /**
     * Sets the send-to destination for two-factor authentication.
     *
     * @param sendTo The send-to destination.
     */
    public void setSendTo(VerificationType sendTo) {
        this.sendTo = sendTo;
    }
}
