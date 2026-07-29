package com.cloud.optimizer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_mfa")
public class UserMfa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "mfa_id", updatable = false, nullable = false)
    private String mfaId;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "totp_secret", nullable = false)
    private String totpSecret;

    @Column(name = "backup_codes", length = 1000)
    private String backupCodes;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    @Column(name = "enabled_at")
    private LocalDateTime enabledAt;

    public UserMfa() {}

    public UserMfa(String mfaId, String userId, String totpSecret, String backupCodes, boolean isVerified, LocalDateTime enabledAt) {
        this.mfaId = mfaId;
        this.userId = userId;
        this.totpSecret = totpSecret;
        this.backupCodes = backupCodes;
        this.isVerified = isVerified;
        this.enabledAt = enabledAt;
    }

    public String getMfaId() { return mfaId; }
    public void setMfaId(String mfaId) { this.mfaId = mfaId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTotpSecret() { return totpSecret; }
    public void setTotpSecret(String totpSecret) { this.totpSecret = totpSecret; }

    public String getBackupCodes() { return backupCodes; }
    public void setBackupCodes(String backupCodes) { this.backupCodes = backupCodes; }

    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean verified) { isVerified = verified; }

    public LocalDateTime getEnabledAt() { return enabledAt; }
    public void setEnabledAt(LocalDateTime enabledAt) { this.enabledAt = enabledAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String mfaId;
        private String userId;
        private String totpSecret;
        private String backupCodes;
        private boolean isVerified = false;
        private LocalDateTime enabledAt;

        public Builder mfaId(String mfaId) { this.mfaId = mfaId; return this; }
        public Builder userId(String userId) { this.userId = userId; return this; }
        public Builder totpSecret(String totpSecret) { this.totpSecret = totpSecret; return this; }
        public Builder backupCodes(String backupCodes) { this.backupCodes = backupCodes; return this; }
        public Builder isVerified(boolean isVerified) { this.isVerified = isVerified; return this; }
        public Builder enabledAt(LocalDateTime enabledAt) { this.enabledAt = enabledAt; return this; }

        public UserMfa build() {
            return new UserMfa(mfaId, userId, totpSecret, backupCodes, isVerified, enabledAt);
        }
    }
}
