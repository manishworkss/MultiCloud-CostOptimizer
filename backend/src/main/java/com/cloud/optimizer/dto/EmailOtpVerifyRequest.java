package com.cloud.optimizer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailOtpVerifyRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Email OTP code is required")
    private String otpCode;

    public EmailOtpVerifyRequest() {}

    public EmailOtpVerifyRequest(String email, String otpCode) {
        this.email = email;
        this.otpCode = otpCode;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getOtpCode() { return otpCode; }
    public void setOtpCode(String otpCode) { this.otpCode = otpCode; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String email;
        private String otpCode;

        public Builder email(String email) { this.email = email; return this; }
        public Builder otpCode(String otpCode) { this.otpCode = otpCode; return this; }

        public EmailOtpVerifyRequest build() {
            return new EmailOtpVerifyRequest(email, otpCode);
        }
    }
}
