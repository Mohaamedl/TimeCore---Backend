package com.odin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Objects;

/**
 * Represents a two-factor OTP (One-Time Password).
 */
@Data
@Entity
@Table(name = "two_factor_otps")
public class TwoFactorOTP {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private String jwt;

    /**
     * Default constructor.
     */
    public TwoFactorOTP() {
    }

    /**
     * Gets the associated user.
     *
     * @return The user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the associated user.
     *
     * @param user The user.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the OTP.
     *
     * @return The OTP.
     */
    public String getOtp() {
        return otp;
    }

    /**
     * Sets the OTP.
     *
     * @param otp The OTP.
     */
    public void setOtp(String otp) {
        this.otp = otp;
    }

    /**
     * Gets the JWT.
     *
     * @return The JWT.
     */
    public String getJwt() {
        return jwt;
    }

    /**
     * Sets the JWT.
     *
     * @param jwt The JWT.
     */
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    /**
     * Gets the ID.
     *
     * @return The ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID.
     *
     * @param id The ID.
     */
    public void setId(String id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o)
    {
        if (!(o  instanceof TwoFactorOTP))
            return false;
        TwoFactorOTP other = (TwoFactorOTP) o;
        return (Objects.equals(other.id,this.id) && Objects.equals(other.otp, this.otp) &&
                Objects.equals(other.jwt,this.jwt));
    }

    @Override
    public int hashCode()
    {
        int prime =  31;
        int result = prime;
        result += prime * result + (this.id == null ? 0 : this.id.hashCode());
        result += prime * result + (this.otp == null ? 0 : this.otp.hashCode());
        result += prime * result + (this.jwt == null ? 0: this.jwt.hashCode());
        return result;
    }


}
