package com.cloud.optimizer.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_mfa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private String backupCodes; // Comma-separated single-use recovery codes

    @Column(name = "is_verified", nullable = false)
    @Builder.Default
    private boolean isVerified = false;

    @Column(name = "enabled_at")
    private LocalDateTime enabledAt;
}
