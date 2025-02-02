package com.odin.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private boolean status;
    private String message;
    private boolean isTwoFactorAuthEnabled;
    private String session;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public void setTwoFactorAuthEnabled(boolean twoFactorAuthEnabled) {
        isTwoFactorAuthEnabled = twoFactorAuthEnabled;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public boolean isTwoFactorAuthEnabled() {
        return isTwoFactorAuthEnabled;
    }

    public String getJwt() {
        return jwt;
    }

    public String getSession() {
        return session;
    }

    public boolean isStatus() {
        return status;
    }
}
