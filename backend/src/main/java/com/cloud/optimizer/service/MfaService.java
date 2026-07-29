package com.cloud.optimizer.service;

import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class MfaService {

    private static final int SECRET_SIZE = 20;
    private static final int TIME_STEP_SECONDS = 30;
    private static final int WINDOW_SIZE = 1;

    @Value("${app.mfa.issuer:CostMatrix}")
    private String issuer = "CostMatrix";

    private final Base32 base32 = new Base32();
    private final SecureRandom random = new SecureRandom();

    public MfaService() {
        this.issuer = "CostMatrix";
    }

    public MfaService(String issuer) {
        this.issuer = issuer != null ? issuer : "CostMatrix";
    }

    public String generateSecretKey() {
        byte[] buffer = new byte[SECRET_SIZE];
        random.nextBytes(buffer);
        return base32.encodeToString(buffer).replace("=", "");
    }

    public String generateQrCodeUrl(String email, String secret) {
        String safeIssuer = (issuer != null && !issuer.isBlank()) ? issuer : "CostMatrix";
        String encodedIssuer = URLEncoder.encode(safeIssuer, StandardCharsets.UTF_8);
        String encodedEmail = URLEncoder.encode(email != null ? email : "user@costmatrix.com", StandardCharsets.UTF_8);
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s&algorithm=SHA1&digits=6&period=30",
                encodedIssuer, encodedEmail, secret, encodedIssuer);
    }

    public List<String> generateBackupCodes() {
        List<String> codes = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            byte[] bytes = new byte[5];
            random.nextBytes(bytes);
            codes.add(base32.encodeToString(bytes).substring(0, 8).toUpperCase());
        }
        return codes;
    }

    public boolean verifyTotp(String secretKey, String codeStr) {
        if (codeStr == null || codeStr.trim().length() != 6) {
            return false;
        }

        int inputCode;
        try {
            inputCode = Integer.parseInt(codeStr.trim());
        } catch (NumberFormatException e) {
            return false;
        }

        byte[] decodedKey = base32.decode(secretKey);
        long currentBucket = System.currentTimeMillis() / 1000L / TIME_STEP_SECONDS;

        for (int i = -WINDOW_SIZE; i <= WINDOW_SIZE; i++) {
            long targetBucket = currentBucket + i;
            if (generateCodeForBucket(decodedKey, targetBucket) == inputCode) {
                return true;
            }
        }
        return false;
    }

    private int generateCodeForBucket(byte[] key, long timeBucket) {
        try {
            byte[] data = ByteBuffer.allocate(8).putLong(timeBucket).array();
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(key, "HmacSHA1"));
            byte[] hash = mac.doFinal(data);

            int offset = hash[hash.length - 1] & 0x0F;
            int truncatedHash = ((hash[offset] & 0x7F) << 24) |
                                ((hash[offset + 1] & 0xFF) << 16) |
                                ((hash[offset + 2] & 0xFF) << 8) |
                                (hash[offset + 3] & 0xFF);

            return truncatedHash % 1_000_000;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error calculating TOTP code", e);
        }
    }
}
