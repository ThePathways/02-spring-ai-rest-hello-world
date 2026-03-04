package com.example.__spring_ai_rest_hello_world.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request DTO for chat endpoint
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {

    /**
     * The user's message
     */
    private String message;

    /**
     * Optional: List of previous messages for context (simple implementation)
     */
    private List<ChatMessage> messages;

    /**
     * Optional: Model to use (defaults to configured model)
     */
    private String model;

    /**
     * Optional: Temperature setting
     */
    private Double temperature;

    /**
     * Nested class for chat messages
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatMessage {
        private String role; // "user" or "assistant"
        private String content;
    }
}

