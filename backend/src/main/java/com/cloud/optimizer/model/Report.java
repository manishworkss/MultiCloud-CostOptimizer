package com.cloud.optimizer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
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

    public Report() {}

    public Report(String reportId, String recommendationId, String pdfPath, LocalDateTime generatedDate) {
        this.reportId = reportId;
        this.recommendationId = recommendationId;
        this.pdfPath = pdfPath;
        this.generatedDate = generatedDate;
    }

    @PrePersist
    protected void onCreate() {
        this.generatedDate = LocalDateTime.now();
    }

    public String getReportId() { return reportId; }
    public void setReportId(String reportId) { this.reportId = reportId; }

    public String getRecommendationId() { return recommendationId; }
    public void setRecommendationId(String recommendationId) { this.recommendationId = recommendationId; }

    public String getPdfPath() { return pdfPath; }
    public void setPdfPath(String pdfPath) { this.pdfPath = pdfPath; }

    public LocalDateTime getGeneratedDate() { return generatedDate; }
    public void setGeneratedDate(LocalDateTime generatedDate) { this.generatedDate = generatedDate; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String reportId;
        private String recommendationId;
        private String pdfPath;
        private LocalDateTime generatedDate;

        public Builder reportId(String reportId) { this.reportId = reportId; return this; }
        public Builder recommendationId(String recommendationId) { this.recommendationId = recommendationId; return this; }
        public Builder pdfPath(String pdfPath) { this.pdfPath = pdfPath; return this; }
        public Builder generatedDate(LocalDateTime generatedDate) { this.generatedDate = generatedDate; return this; }

        public Report build() {
            return new Report(reportId, recommendationId, pdfPath, generatedDate);
        }
    }
}
