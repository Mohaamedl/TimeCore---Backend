package com.odin.Model;

import com.odin.domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VerificationCode {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String otp;

    @OneToOne
    private User user;

    private String email;

    private String mobile;

    private VerificationType verificationType;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setVerificationType(VerificationType verificationType) {
        this.verificationType = verificationType;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getOtp() {
        return otp;
    }

    public User getUser() {
        return user;
    }

    public VerificationType getVerificationType() {
        return verificationType;
    }
}
