package com.odin.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.odin.domain.USER_ROLE;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Represents a user in the system.
 */
@Data
@Entity
@Table(name = "users")
public class User implements Comparable<User>{
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

    @Column(name = "mobile", nullable = true)
    private String mobile;


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

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private Set<Event> events = new HashSet<>();

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the full name of the user.
     *
     * @param fullname The full name.
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id The ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the role of the user.
     *
     * @param role The role.
     */
    public void setRole(USER_ROLE role) {
        this.role = role;
    }

    /**
     * Sets the two-factor authentication settings for the user.
     *
     * @param twoFactorAuth The two-factor authentication settings.
     */
    public void setTwoFactorAuth(TwoFactorAuth twoFactorAuth) {
        this.twoFactorAuth = twoFactorAuth;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the full name of the user.
     *
     * @return The full name.
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Gets the ID of the user.
     *
     * @return The ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the role of the user.
     *
     * @return The role.
     */
    public USER_ROLE getRole() {
        return role;
    }

    /**
     * Gets the two-factor authentication settings for the user.
     *
     * @return The two-factor authentication settings.
     */
    public TwoFactorAuth getTwoFactorAuth() {
        return twoFactorAuth;
    }

    /**
     * Gets the events associated with the user.
     *
     * @return The set of events.
     */
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * Sets the events associated with the user.
     *
     * @param events The set of events.
     */
    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(fullname, user.fullname) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(mobile, user.mobile) && Objects.equals(twoFactorAuth, user.twoFactorAuth) && role == user.role && Objects.equals(events, user.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullname, email, password, mobile, twoFactorAuth, role, events);
    }


    /**
     * @param o the object to be compared. 
     * @return
     */
    @Override
    public int compareTo(User o) {
        if (o.equals(this))
            return 0;
        return this.fullname.compareTo(o.fullname);
    }
}
