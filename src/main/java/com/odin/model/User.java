package com.odin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.odin.domain.USER_ROLE;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "enabled",
            column = @Column(name = "two_factor_auth_enabled", nullable = false)),
        @AttributeOverride(name = "send_to",
            column = @Column(name = "two_factor_auth_send_to"))
    })
    private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private USER_ROLE role = USER_ROLE.USER;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(USER_ROLE role) {
        this.role = role;
    }

    public void setTwoFactorAuth(TwoFactorAuth twoFactorAuth) {
        this.twoFactorAuth = twoFactorAuth;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public USER_ROLE getRole() {
        return role;
    }

    public TwoFactorAuth getTwoFactorAuth() {
        return twoFactorAuth;
    }
}
