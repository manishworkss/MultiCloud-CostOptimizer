package com.cloud.optimizer.controller;

import com.cloud.optimizer.service.CsvReportService;
import com.cloud.optimizer.service.PdfReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final PdfReportService pdfReportService;
    private final CsvReportService csvReportService;

    public ReportController(PdfReportService pdfReportService, CsvReportService csvReportService) {
        this.pdfReportService = pdfReportService;
        this.csvReportService = csvReportService;
    }

    @GetMapping("/pdf/{requestId}")
    public ResponseEntity<byte[]> downloadPdfReport(@PathVariable String requestId) {
        byte[] pdfContents = pdfReportService.generateExecutivePdfReport(requestId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "CostMatrix_Optimization_Report_" + requestId + ".pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContents);
    }

    @GetMapping("/csv/{requestId}")
    public ResponseEntity<String> downloadCsvReport(@PathVariable String requestId) {
        String csvData = csvReportService.generateCsvReport(requestId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "CostMatrix_Optimization_Report_" + requestId + ".csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(csvData);
    }
}
