package com.cloud.optimizer;

import com.cloud.optimizer.dto.RecommendationResponseDto;
import com.cloud.optimizer.model.DeploymentRequest;
import com.cloud.optimizer.repository.DeploymentRequestRepository;
import com.cloud.optimizer.service.PdfReportService;
import com.cloud.optimizer.service.RecommendationEngineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PdfReportServiceTest {

    private PdfReportService pdfReportService;
    private DeploymentRequestRepository deploymentRequestRepository;
    private RecommendationEngineService recommendationEngineService;

    @BeforeEach
    void setUp() {
        deploymentRequestRepository = Mockito.mock(DeploymentRequestRepository.class);
        recommendationEngineService = Mockito.mock(RecommendationEngineService.class);
        pdfReportService = new PdfReportService(deploymentRequestRepository, recommendationEngineService);
    }

    @Test
    void testGenerateExecutivePdfReport() {
        String requestId = "req-123";
        DeploymentRequest request = DeploymentRequest.builder()
                .requestId(requestId)
                .cpu("4 Cores")
                .ram("16 GB")
                .storage("200 GB SSD")
                .operatingSystem("Ubuntu Linux 22.04")
                .databaseType("PostgreSQL")
                .bandwidth("500 GB")
                .region("US-East (N. Virginia)")
                .build();

        RecommendationResponseDto dto = RecommendationResponseDto.builder()
                .recommendationId("rec-1")
                .requestId(requestId)
                .providerId("OCI")
                .providerName("Oracle Cloud Infrastructure")
                .serviceName("VM.Standard.E4.Flex")
                .totalMonthlyCost(new BigDecimal("210.50"))
                .totalYearlyCost(new BigDecimal("2526.00"))
                .estimatedSavings(new BigDecimal("95.00"))
                .recommendationScore(new BigDecimal("94.50"))
                .regionSlaUptime(99.95)
                .region("US-East (N. Virginia)")
                .build();

        Mockito.when(deploymentRequestRepository.findById(requestId)).thenReturn(Optional.of(request));
        Mockito.when(recommendationEngineService.getRecommendationsForRequest(requestId)).thenReturn(List.of(dto));

        byte[] pdfBytes = pdfReportService.generateExecutivePdfReport(requestId);

        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
        // Assert PDF header bytes %PDF-
        String header = new String(pdfBytes, 0, 5);
        assertEquals("%PDF-", header);
    }
}
