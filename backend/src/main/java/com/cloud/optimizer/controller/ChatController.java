package com.cloud.optimizer.controller;

import com.cloud.optimizer.dto.ChatRequest;
import com.cloud.optimizer.dto.ChatResponse;
import com.cloud.optimizer.service.AiChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
@CrossOrigin(origins = "*") // Allows the React frontend to communicate with it
public class ChatController {

    private final AiChatService aiChatService;

    public ChatController(AiChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ChatResponse("Message cannot be empty."));
        }

        String aiResponse = aiChatService.getChatResponse(request.getMessage());
        return ResponseEntity.ok(new ChatResponse(aiResponse));
    }
}
