package com.cloud.optimizer.service;

import com.cloud.optimizer.dto.RecommendationResponseDto;
import com.cloud.optimizer.model.DeploymentRequest;
import com.cloud.optimizer.repository.DeploymentRequestRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfReportService {

    private final DeploymentRequestRepository deploymentRequestRepository;
    private final RecommendationEngineService recommendationEngineService;

    public PdfReportService(DeploymentRequestRepository deploymentRequestRepository,
                            RecommendationEngineService recommendationEngineService) {
        this.deploymentRequestRepository = deploymentRequestRepository;
        this.recommendationEngineService = recommendationEngineService;
    }

    public byte[] generateExecutivePdfReport(String requestId) {
        DeploymentRequest request = deploymentRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Deployment request not found: " + requestId));

        List<RecommendationResponseDto> recommendations = recommendationEngineService.getRecommendationsForRequest(requestId);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);

        document.open();

        // 1. Header Title
        Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD, new Color(6, 182, 212)); // Cyan Accent
        Paragraph title = new Paragraph("CostMatrix: Multi-Cloud Optimization Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Font subtitleFont = new Font(Font.HELVETICA, 10, Font.ITALIC, Color.GRAY);
        Paragraph subtitle = new Paragraph("Generated on " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " UTC", subtitleFont);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitle);

        document.add(new Paragraph(" ")); // Spacer

        // 2. Deployment Specifications Box
        Font sectionFont = new Font(Font.HELVETICA, 14, Font.BOLD, new Color(17, 24, 39));
        document.add(new Paragraph("1. Workload Specifications", sectionFont));
        document.add(new Paragraph(" "));

        PdfPTable specTable = new PdfPTable(2);
        specTable.setWidthPercentage(100);

        addSpecRow(specTable, "Target Region", request.getRegion());
        addSpecRow(specTable, "vCPU Cores", request.getCpu());
        addSpecRow(specTable, "RAM Capacity", request.getRam());
        addSpecRow(specTable, "Storage Volume", request.getStorage());
        addSpecRow(specTable, "Operating System", request.getOperatingSystem());
        addSpecRow(specTable, "Database Engine", request.getDatabaseType());
        addSpecRow(specTable, "Monthly Egress Traffic", request.getBandwidth());

        document.add(specTable);
        document.add(new Paragraph(" "));

        // 3. Multi-Cloud Provider Cost Comparison Table
        document.add(new Paragraph("2. Provider Cost & Ranking Comparison", sectionFont));
        document.add(new Paragraph(" "));

        PdfPTable comparisonTable = new PdfPTable(5);
        comparisonTable.setWidthPercentage(100);

        // Headers
        String[] headers = {"Provider", "Monthly Cost", "Yearly Cost", "Est. Savings", "Score"};
        for (String head : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(head, new Font(Font.HELVETICA, 10, Font.BOLD, Color.WHITE)));
            cell.setBackgroundColor(new Color(15, 23, 42)); // Dark Slate
            cell.setPadding(8);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            comparisonTable.addCell(cell);
        }

        for (RecommendationResponseDto dto : recommendations) {
            addTableCell(comparisonTable, dto.getProviderName());
            addTableCell(comparisonTable, "$" + dto.getTotalMonthlyCost());
            addTableCell(comparisonTable, "$" + dto.getTotalYearlyCost());
            addTableCell(comparisonTable, "$" + dto.getEstimatedSavings());
            addTableCell(comparisonTable, dto.getRecommendationScore() + " / 100");
        }

        document.add(comparisonTable);
        document.add(new Paragraph(" "));

        // 4. Winning Provider Recommendation Highlight Box
        if (!recommendations.isEmpty()) {
            RecommendationResponseDto winner = recommendations.get(0);
            document.add(new Paragraph("3. Recommended Placement Strategy", sectionFont));
            document.add(new Paragraph(" "));

            Font winFont = new Font(Font.HELVETICA, 11, Font.BOLD, new Color(16, 185, 129)); // Emerald Green
            Paragraph winnerText = new Paragraph("Optimal Provider: " + winner.getProviderName() + " (" + winner.getServiceName() + ")\n" +
                    "Projected Monthly TCO: $" + winner.getTotalMonthlyCost() + " | Estimated Annual Savings: $" + winner.getEstimatedSavings().multiply(new java.math.BigDecimal("12")), winFont);
            
            PdfPCell winCell = new PdfPCell(winnerText);
            winCell.setPadding(12);
            winCell.setBackgroundColor(new Color(236, 253, 245)); // Light Emerald tint
            winCell.setBorderColor(new Color(16, 185, 129));

            PdfPTable winTable = new PdfPTable(1);
            winTable.setWidthPercentage(100);
            winTable.addCell(winCell);
            document.add(winTable);
        }

        document.close();
        return out.toByteArray();
    }

    private void addSpecRow(PdfPTable table, String label, String value) {
        PdfPCell cell1 = new PdfPCell(new Phrase(label, new Font(Font.HELVETICA, 10, Font.BOLD)));
        cell1.setPadding(5);
        cell1.setBackgroundColor(new Color(243, 244, 246));

        PdfPCell cell2 = new PdfPCell(new Phrase(value != null ? value : "N/A", new Font(Font.HELVETICA, 10)));
        cell2.setPadding(5);

        table.addCell(cell1);
        table.addCell(cell2);
    }

    private void addTableCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(Font.HELVETICA, 9)));
        cell.setPadding(6);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }
}
