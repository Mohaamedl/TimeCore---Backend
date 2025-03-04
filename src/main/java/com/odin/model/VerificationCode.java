package com.odin.model;

import com.odin.domain.VerificationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Represents a verification code for user authentication.
 */
@Entity
@Data
@NoArgsConstructor
public class VerificationCode {

    /**
     * Unique identifier for the verification code
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The actual OTP (One-Time Password) code
     */
    private String otp;

    /**
     * User associated with this verification code
     */
    @OneToOne
    private User user;

    /**
     * Email associated with this verification code
     */
    private String email;

    /**
     * Mobile number associated with this verification code
     */
    private String mobile;

    /**
     * Type of verification (email or mobile)
     */
    private VerificationType verificationType;

    /**
     * Sets the email associated with the verification code.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the unique identifier for the verification code.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the mobile number associated with the verification code.
     *
     * @param mobile the mobile number to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Sets the OTP (One-Time Password) for the verification code.
     *
     * @param otp the otp to set
     */
    public void setOtp(String otp) {
        this.otp = otp;
    }

    /**
     * Sets the user associated with the verification code.
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets the verification type for the verification code.
     *
     * @param verificationType the verificationType to set
     */
    public void setVerificationType(VerificationType verificationType) {
        this.verificationType = verificationType;
    }

    /**
     * Gets the email associated with the verification code.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the unique identifier for the verification code.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the mobile number associated with the verification code.
     *
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Gets the OTP (One-Time Password) for the verification code.
     *
     * @return the otp
     */
    public String getOtp() {
        return otp;
    }

    /**
     * Gets the user associated with the verification code.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets the verification type for the verification code.
     *
     * @return the verificationType
     */
    public VerificationType getVerificationType() {
        return verificationType;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof VerificationCode))
            return false;
        VerificationCode other = (VerificationCode) o;
        return Objects.equals(other.id, this.id) && Objects.equals(this.email, other.email) &&
                Objects.equals(other.otp, this.otp) && Objects.equals(other.user,this.user) &&
                Objects.equals(other.mobile, this.mobile) && Objects.equals(other.verificationType, this.verificationType);
    }

    @Override
    public int hashCode()
    {
        int prime =  31;
        int result = prime;
        result += result *  prime + (this.id == null ? 0 : this.id.hashCode());
        result += result *  prime + (this.otp == null ? 0 : this.otp.hashCode());
        result += result *  prime + (this.email == null ? 0 : this.email.hashCode());
        result += result *  prime + (this.mobile == null ? 0 : this.mobile.hashCode());
        result += result *  prime + (this.verificationType == null ? 0 : this.verificationType.hashCode());
        return result;
    }


}
