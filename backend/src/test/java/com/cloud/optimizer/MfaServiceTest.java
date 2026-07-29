package com.cloud.optimizer;

import com.cloud.optimizer.service.MfaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MfaServiceTest {

    private MfaService mfaService;

    @BeforeEach
    void setUp() {
        mfaService = new MfaService();
    }

    @Test
    void testGenerateSecretKey() {
        String secretKey = mfaService.generateSecretKey();
        assertNotNull(secretKey);
        assertTrue(secretKey.length() >= 16);
    }

    @Test
    void testGenerateQrCodeUrl() {
        String secretKey = mfaService.generateSecretKey();
        String qrUrl = mfaService.generateQrCodeUrl("test@example.com", secretKey);
        assertNotNull(qrUrl);
        assertTrue(qrUrl.startsWith("otpauth://totp/"));
        assertTrue(qrUrl.contains("secret=" + secretKey));
    }

    @Test
    void testGenerateBackupCodes() {
        List<String> codes = mfaService.generateBackupCodes();
        assertNotNull(codes);
        assertEquals(8, codes.size());
        for (String code : codes) {
            assertEquals(8, code.length());
        }
    }
}
