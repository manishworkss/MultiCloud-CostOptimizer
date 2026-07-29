package com.cloud.optimizer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", updatable = false, nullable = false)
    private String userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "role", nullable = false)
    private String role; // e.g. ROLE_USER, ROLE_ADMIN

    @Column(name = "mfa_enabled", nullable = false)
    private boolean mfaEnabled = false;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;

    @Column(name = "email_otp_code")
    private String emailOtpCode;

    @Column(name = "email_otp_expiry")
    private LocalDateTime emailOtpExpiry;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public User() {}

    public User(String userId, String name, String email, String passwordHash, String role, boolean mfaEnabled, boolean emailVerified, String emailOtpCode, LocalDateTime emailOtpExpiry, LocalDateTime createdAt) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.mfaEnabled = mfaEnabled;
        this.emailVerified = emailVerified;
        this.emailOtpCode = emailOtpCode;
        this.emailOtpExpiry = emailOtpExpiry;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isMfaEnabled() { return mfaEnabled; }
    public void setMfaEnabled(boolean mfaEnabled) { this.mfaEnabled = mfaEnabled; }

    public boolean isEmailVerified() { return emailVerified; }
    public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }

    public String getEmailOtpCode() { return emailOtpCode; }
    public void setEmailOtpCode(String emailOtpCode) { this.emailOtpCode = emailOtpCode; }

    public LocalDateTime getEmailOtpExpiry() { return emailOtpExpiry; }
    public void setEmailOtpExpiry(LocalDateTime emailOtpExpiry) { this.emailOtpExpiry = emailOtpExpiry; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String userId;
        private String name;
        private String email;
        private String passwordHash;
        private String role = "ROLE_USER";
        private boolean mfaEnabled = false;
        private boolean emailVerified = false;
        private String emailOtpCode;
        private LocalDateTime emailOtpExpiry;
        private LocalDateTime createdAt;

        public Builder userId(String userId) { this.userId = userId; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder passwordHash(String passwordHash) { this.passwordHash = passwordHash; return this; }
        public Builder role(String role) { this.role = role; return this; }
        public Builder mfaEnabled(boolean mfaEnabled) { this.mfaEnabled = mfaEnabled; return this; }
        public Builder emailVerified(boolean emailVerified) { this.emailVerified = emailVerified; return this; }
        public Builder emailOtpCode(String emailOtpCode) { this.emailOtpCode = emailOtpCode; return this; }
        public Builder emailOtpExpiry(LocalDateTime emailOtpExpiry) { this.emailOtpExpiry = emailOtpExpiry; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public User build() {
            return new User(userId, name, email, passwordHash, role, mfaEnabled, emailVerified, emailOtpCode, emailOtpExpiry, createdAt);
        }
    }
}
