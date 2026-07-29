package com.cloud.optimizer.dto;

import jakarta.validation.constraints.NotBlank;

public class MfaVerifyRequest {

    @NotBlank(message = "Pre-MFA token or user ID is required")
    private String preMfaToken;

    @NotBlank(message = "TOTP code is required")
    private String totpCode;

    public MfaVerifyRequest() {}

    public MfaVerifyRequest(String preMfaToken, String totpCode) {
        this.preMfaToken = preMfaToken;
        this.totpCode = totpCode;
    }

    public String getPreMfaToken() { return preMfaToken; }
    public void setPreMfaToken(String preMfaToken) { this.preMfaToken = preMfaToken; }

    public String getTotpCode() { return totpCode; }
    public void setTotpCode(String totpCode) { this.totpCode = totpCode; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String preMfaToken;
        private String totpCode;

        public Builder preMfaToken(String preMfaToken) { this.preMfaToken = preMfaToken; return this; }
        public Builder totpCode(String totpCode) { this.totpCode = totpCode; return this; }

        public MfaVerifyRequest build() {
            return new MfaVerifyRequest(preMfaToken, totpCode);
        }
    }
}
