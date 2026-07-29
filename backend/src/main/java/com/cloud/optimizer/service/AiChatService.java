package com.cloud.optimizer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiChatService {

    private static final Logger logger = LoggerFactory.getLogger(AiChatService.class);
    
    private final RestTemplate restTemplate;

    @Value("${app.ai.gemini.api-key:}")
    private String geminiApiKey;

    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=";

    private static final String SYSTEM_PROMPT = 
        "You are CostMatrix-AI, an expert FinOps assistant and Help Desk agent for the Multi-Cloud Cost Optimizer platform. " +
        "You MUST ONLY answer questions related to cloud infrastructure, AWS/Azure/GCP/OCI pricing, deployment specifications, " +
        "FinOps principles, and how to use this platform. " +
        "If the user asks about general knowledge, programming outside of this context, history, or anything else unrelated to this project, " +
        "you MUST explicitly decline to answer and remind them of your purpose. Be professional, concise, and helpful.";

    public AiChatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getChatResponse(String userMessage) {
        if (geminiApiKey == null || geminiApiKey.isEmpty()) {
            logger.warn("Gemini API Key is not configured. Falling back to mock response.");
            return "Warning: The AI Help Desk is currently unconfigured. Please provide a GEMINI_API_KEY to the backend to enable live AI responses. (Mock response to: '" + userMessage + "')";
        }

        try {
            String url = GEMINI_API_URL + geminiApiKey;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Constructing the Gemini API payload using Maps
            Map<String, Object> requestBody = new HashMap<>();

            // 1. System Instruction
            Map<String, Object> systemInstruction = new HashMap<>();
            Map<String, Object> systemParts = new HashMap<>();
            systemParts.put("text", SYSTEM_PROMPT);
            systemInstruction.put("parts", systemParts);
            requestBody.put("system_instruction", systemInstruction);

            // 2. User Message Content
            Map<String, Object> content = new HashMap<>();
            Map<String, Object> userParts = new HashMap<>();
            userParts.put("text", userMessage);
            content.put("parts", List.of(userParts));
            requestBody.put("contents", List.of(content));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
            Map<String, Object> responseBody = response.getBody();

            if (responseBody != null && responseBody.containsKey("candidates")) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
                if (!candidates.isEmpty()) {
                    Map<String, Object> candidate = candidates.get(0);
                    Map<String, Object> contentMap = (Map<String, Object>) candidate.get("content");
                    List<Map<String, Object>> parts = (List<Map<String, Object>>) contentMap.get("parts");
                    if (!parts.isEmpty()) {
                        return (String) parts.get(0).get("text");
                    }
                }
            }
            return "I'm sorry, I couldn't generate a response at this time.";

        } catch (Exception e) {
            logger.error("Error communicating with Gemini API", e);
            return "An error occurred while trying to reach the AI service. Please try again later.";
        }
    }
}
