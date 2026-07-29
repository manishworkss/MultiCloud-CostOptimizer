package com.cloud.optimizer.dto;

public class AuthResponse {

    private String userId;
    private String email;
    private String name;
    private String role;
    private String accessToken;
    private String refreshToken;
    private boolean mfaRequired;
    private boolean mfaEnabled;
    private boolean emailVerificationRequired;
    private String preMfaToken;

    public AuthResponse() {}

    public AuthResponse(String userId, String email, String name, String role, String accessToken, String refreshToken, boolean mfaRequired, boolean mfaEnabled, boolean emailVerificationRequired, String preMfaToken) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.role = role;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.mfaRequired = mfaRequired;
        this.mfaEnabled = mfaEnabled;
        this.emailVerificationRequired = emailVerificationRequired;
        this.preMfaToken = preMfaToken;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public boolean isMfaRequired() { return mfaRequired; }
    public void setMfaRequired(boolean mfaRequired) { this.mfaRequired = mfaRequired; }

    public boolean isMfaEnabled() { return mfaEnabled; }
    public void setMfaEnabled(boolean mfaEnabled) { this.mfaEnabled = mfaEnabled; }

    public boolean isEmailVerificationRequired() { return emailVerificationRequired; }
    public void setEmailVerificationRequired(boolean emailVerificationRequired) { this.emailVerificationRequired = emailVerificationRequired; }

    public String getPreMfaToken() { return preMfaToken; }
    public void setPreMfaToken(String preMfaToken) { this.preMfaToken = preMfaToken; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String userId;
        private String email;
        private String name;
        private String role;
        private String accessToken;
        private String refreshToken;
        private boolean mfaRequired;
        private boolean mfaEnabled;
        private boolean emailVerificationRequired;
        private String preMfaToken;

        public Builder userId(String userId) { this.userId = userId; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder role(String role) { this.role = role; return this; }
        public Builder accessToken(String accessToken) { this.accessToken = accessToken; return this; }
        public Builder refreshToken(String refreshToken) { this.refreshToken = refreshToken; return this; }
        public Builder mfaRequired(boolean mfaRequired) { this.mfaRequired = mfaRequired; return this; }
        public Builder mfaEnabled(boolean mfaEnabled) { this.mfaEnabled = mfaEnabled; return this; }
        public Builder emailVerificationRequired(boolean emailVerificationRequired) { this.emailVerificationRequired = emailVerificationRequired; return this; }
        public Builder preMfaToken(String preMfaToken) { this.preMfaToken = preMfaToken; return this; }

        public AuthResponse build() {
            return new AuthResponse(userId, email, name, role, accessToken, refreshToken, mfaRequired, mfaEnabled, emailVerificationRequired, preMfaToken);
        }
    }
}
