package com.odin.response;

import lombok.Data;

/**
 * Response object for authentication operations.
 * Contains authentication status, tokens and messages.
 */
@Data
public class AuthResponse {
    
    private String jwt;
    private String message;
    private String session;
    private boolean status;
    private boolean twoFactorAuthEnabled;

    /**
     * Default constructor
     */
    public AuthResponse() {}

    /**
     * Gets the JWT token
     * @return JWT token string
     */
    public String getJwt() {
        return jwt; 
    }

    /**
     * Gets the response message
     * @return Message string
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the response message
     * @param message Message to be set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the session identifier
     * @param session Session ID to be set
     */
    public void setSession(String session) {
        this.session = session;
    }

    /**
     * Sets the JWT token
     * @param jwt JWT token string
     */
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    /**
     * Sets the two-factor authentication status
     * @param twoFactorAuthEnabled 2FA status to be set
     */
    public void setTwoFactorAuthEnabled(boolean twoFactorAuthEnabled) {
        this.twoFactorAuthEnabled = twoFactorAuthEnabled;
    }

    /**
     * Sets the authentication status
     * @param status Status to be set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Gets the session identifier
     * @return Session ID string
     */
    public String getSession() {
        return session;
    }

    /**
     * Gets the status of the authentication
     * @return true if authentication was successful, false otherwise
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Gets the two-factor authentication status
     * @return true if 2FA is enabled, false otherwise
     */
    public boolean isTwoFactorAuthEnabled() {
        return twoFactorAuthEnabled;
    }
}
