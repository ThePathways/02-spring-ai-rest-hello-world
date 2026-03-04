package com.example.__spring_ai_rest_hello_world.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for chat endpoint
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    /**
     * The AI's response message
     */
    private String response;

    /**
     * The model used to generate the response
     */
    private String model;

    /**
     * Number of tokens used in the response
     */
    private Integer tokensUsed;

    /**
     * Whether the request was successful
     */
    private boolean success;

    /**
     * Error message if the request failed
     */
    private String error;
}

