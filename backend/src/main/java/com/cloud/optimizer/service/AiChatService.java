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

    @Value("${app.ai.groq.api-key:}")
    private String groqApiKey;

    private static final String GROQ_API_URL = "https://api.groq.com/openai/v1/chat/completions";

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
        if (groqApiKey == null || groqApiKey.isEmpty()) {
            logger.warn("Groq API Key is not configured. Falling back to mock response.");
            return "Warning: The AI Help Desk is currently unconfigured. Please provide a GROQ_API_KEY to the backend to enable live AI responses. (Mock response to: '" + userMessage + "')";
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(groqApiKey);

            // Constructing the Groq/OpenAI API payload
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "llama-3.1-8b-instant");

            List<Map<String, String>> messages = new ArrayList<>();
            
            // 1. System Instruction
            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", SYSTEM_PROMPT);
            messages.add(systemMessage);

            // 2. User Message Content
            Map<String, String> userMsg = new HashMap<>();
            userMsg.put("role", "user");
            userMsg.put("content", userMessage);
            messages.add(userMsg);
            
            requestBody.put("messages", messages);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(GROQ_API_URL, HttpMethod.POST, entity, Map.class);
            Map<String, Object> responseBody = response.getBody();

            if (responseBody != null && responseBody.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> choice = choices.get(0);
                    Map<String, Object> message = (Map<String, Object>) choice.get("message");
                    if (message != null && message.containsKey("content")) {
                        return (String) message.get("content");
                    }
                }
            }
            return "I'm sorry, I couldn't generate a response at this time.";

        } catch (Exception e) {
            logger.error("Error communicating with Groq API", e);
            return "An error occurred while trying to reach the AI service. Please try again later.";
        }
    }
}
