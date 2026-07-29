package com.cloud.optimizer.dto;

import java.util.List;

public class MfaSetupResponse {

    private String secretKey;
    private String qrCodeUrl;
    private List<String> backupCodes;

    public MfaSetupResponse() {}

    public MfaSetupResponse(String secretKey, String qrCodeUrl, List<String> backupCodes) {
        this.secretKey = secretKey;
        this.qrCodeUrl = qrCodeUrl;
        this.backupCodes = backupCodes;
    }

    public String getSecretKey() { return secretKey; }
    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }

    public String getQrCodeUrl() { return qrCodeUrl; }
    public void setQrCodeUrl(String qrCodeUrl) { this.qrCodeUrl = qrCodeUrl; }

    public List<String> getBackupCodes() { return backupCodes; }
    public void setBackupCodes(List<String> backupCodes) { this.backupCodes = backupCodes; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String secretKey;
        private String qrCodeUrl;
        private List<String> backupCodes;

        public Builder secretKey(String secretKey) { this.secretKey = secretKey; return this; }
        public Builder qrCodeUrl(String qrCodeUrl) { this.qrCodeUrl = qrCodeUrl; return this; }
        public Builder backupCodes(List<String> backupCodes) { this.backupCodes = backupCodes; return this; }

        public MfaSetupResponse build() {
            return new MfaSetupResponse(secretKey, qrCodeUrl, backupCodes);
        }
    }
}
