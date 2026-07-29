package com.cloud.optimizer.service;

import com.cloud.optimizer.dto.*;
import com.cloud.optimizer.model.User;
import com.cloud.optimizer.model.UserMfa;
import com.cloud.optimizer.repository.UserMfaRepository;
import com.cloud.optimizer.repository.UserRepository;
import com.cloud.optimizer.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMfaRepository userMfaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final MfaService mfaService;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository,
                       UserMfaRepository userMfaRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider tokenProvider,
                       MfaService mfaService,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.userMfaRepository = userMfaRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.mfaService = mfaService;
        this.emailService = emailService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            User existing = userRepository.findByEmail(request.getEmail()).orElse(null);
            if (existing != null && !existing.isEmailVerified()) {
                String otp = emailService.generate6DigitOtp();
                existing.setEmailOtpCode(otp);
                existing.setEmailOtpExpiry(LocalDateTime.now().plusMinutes(10));
                userRepository.save(existing);
                boolean sent = emailService.sendEmailOtp(existing.getEmail(), otp);

                return AuthResponse.builder()
                        .userId(existing.getUserId())
                        .email(existing.getEmail())
                        .name(existing.getName())
                        .role(existing.getRole())
                        .emailVerificationRequired(true)
                        .otpForDevTesting(sent ? null : otp)
                        .build();
            }
            throw new IllegalArgumentException("Email is already registered and verified");
        }

        String role = (request.getRole() != null && !request.getRole().isBlank()) 
                ? request.getRole() : "ROLE_USER";

        String otp = emailService.generate6DigitOtp();

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .mfaEnabled(false)
                .emailVerified(false)
                .emailOtpCode(otp)
                .emailOtpExpiry(LocalDateTime.now().plusMinutes(10))
                .build();

        userRepository.save(user);

        boolean sent = emailService.sendEmailOtp(user.getEmail(), otp);

        return AuthResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .emailVerificationRequired(true)
                .otpForDevTesting(sent ? null : otp)
                .build();
    }

    @Transactional
    public AuthResponse verifyEmailOtp(EmailOtpVerifyRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.isEmailVerified()) {
            throw new IllegalArgumentException("Email is already verified");
        }

        if (user.getEmailOtpCode() == null || !user.getEmailOtpCode().equals(request.getOtpCode().trim())) {
            throw new IllegalArgumentException("Invalid Email Verification OTP code");
        }

        if (user.getEmailOtpExpiry() != null && user.getEmailOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Email OTP has expired. Please request a new code.");
        }

        user.setEmailVerified(true);
        user.setEmailOtpCode(null);
        user.setEmailOtpExpiry(null);
        userRepository.save(user);

        String accessToken = tokenProvider.generateAccessToken(user.getUserId(), user.getEmail(), user.getRole());
        String refreshToken = tokenProvider.generateRefreshToken(user.getUserId());

        return AuthResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .mfaEnabled(false)
                .emailVerificationRequired(false)
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        if (!user.isEmailVerified()) {
            String otp = emailService.generate6DigitOtp();
            user.setEmailOtpCode(otp);
            user.setEmailOtpExpiry(LocalDateTime.now().plusMinutes(10));
            userRepository.save(user);
            boolean sent = emailService.sendEmailOtp(user.getEmail(), otp);

            return AuthResponse.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .role(user.getRole())
                    .emailVerificationRequired(true)
                    .otpForDevTesting(sent ? null : otp)
                    .build();
        }

        if (user.isMfaEnabled()) {
            String preMfaToken = tokenProvider.generatePreMfaToken(user.getUserId(), user.getEmail());
            return AuthResponse.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .role(user.getRole())
                    .mfaEnabled(true)
                    .mfaRequired(true)
                    .preMfaToken(preMfaToken)
                    .build();
        }

        String accessToken = tokenProvider.generateAccessToken(user.getUserId(), user.getEmail(), user.getRole());
        String refreshToken = tokenProvider.generateRefreshToken(user.getUserId());

        return AuthResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .mfaEnabled(false)
                .emailVerificationRequired(false)
                .build();
    }

    @Transactional
    public AuthResponse googleAuth(GoogleAuthRequest request) {
        String email = request.getEmail();
        String name = request.getName() != null ? request.getName() : "Google User";

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Invalid Google token payload");
        }

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            user = User.builder()
                    .name(name)
                    .email(email)
                    .passwordHash(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .role("ROLE_USER")
                    .mfaEnabled(false)
                    .emailVerified(true)
                    .build();
            userRepository.save(user);
        }

        String accessToken = tokenProvider.generateAccessToken(user.getUserId(), user.getEmail(), user.getRole());
        String refreshToken = tokenProvider.generateRefreshToken(user.getUserId());

        return AuthResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .mfaEnabled(user.isMfaEnabled())
                .emailVerificationRequired(false)
                .build();
    }

    @Transactional
    public MfaSetupResponse setupMfa(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String secretKey = mfaService.generateSecretKey();
        String qrCodeUrl = mfaService.generateQrCodeUrl(user.getEmail(), secretKey);
        List<String> backupCodes = mfaService.generateBackupCodes();

        UserMfa userMfa = userMfaRepository.findByUserId(userId)
                .orElseGet(() -> UserMfa.builder().userId(userId).build());

        userMfa.setTotpSecret(secretKey);
        userMfa.setBackupCodes(String.join(",", backupCodes));
        userMfa.setVerified(false);
        userMfaRepository.save(userMfa);

        return MfaSetupResponse.builder()
                .secretKey(secretKey)
                .qrCodeUrl(qrCodeUrl)
                .backupCodes(backupCodes)
                .build();
    }

    @Transactional
    public AuthResponse verifyMfa(MfaVerifyRequest request) {
        String tokenOrUserId = request.getPreMfaToken();
        String userId;

        if (tokenProvider.validateToken(tokenOrUserId)) {
            userId = tokenProvider.getUserIdFromToken(tokenOrUserId);
        } else {
            userId = tokenOrUserId;
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserMfa userMfa = userMfaRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("MFA not configured for this user"));

        boolean isValid = mfaService.verifyTotp(userMfa.getTotpSecret(), request.getTotpCode());

        if (!isValid && userMfa.getBackupCodes() != null) {
            List<String> backupList = new ArrayList<>(Arrays.asList(userMfa.getBackupCodes().split(",")));
            String upperCode = request.getTotpCode().trim().toUpperCase();
            if (backupList.contains(upperCode)) {
                backupList.remove(upperCode);
                userMfa.setBackupCodes(String.join(",", backupList));
                isValid = true;
            }
        }

        if (!isValid) {
            throw new IllegalArgumentException("Invalid 2FA authentication code");
        }

        if (!user.isMfaEnabled() || !userMfa.isVerified()) {
            user.setMfaEnabled(true);
            userMfa.setVerified(true);
            userMfa.setEnabledAt(LocalDateTime.now());
            userRepository.save(user);
            userMfaRepository.save(userMfa);
        }

        String accessToken = tokenProvider.generateAccessToken(user.getUserId(), user.getEmail(), user.getRole());
        String refreshToken = tokenProvider.generateRefreshToken(user.getUserId());

        return AuthResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .mfaEnabled(true)
                .mfaRequired(false)
                .build();
    }
}
