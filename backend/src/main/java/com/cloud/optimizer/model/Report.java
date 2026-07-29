package com.cloud.optimizer.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "report_id", updatable = false, nullable = false)
    private String reportId;

    @Column(name = "recommendation_id", nullable = false)
    private String recommendationId;

    @Column(name = "pdf_path", nullable = false)
    private String pdfPath;

    @Column(name = "generated_date", nullable = false, updatable = false)
    private LocalDateTime generatedDate;

    @PrePersist
    protected void onCreate() {
        this.generatedDate = LocalDateTime.now();
    }
}
