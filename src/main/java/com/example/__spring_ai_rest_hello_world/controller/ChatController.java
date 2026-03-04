package com.example.__spring_ai_rest_hello_world.controller;

import com.example.__spring_ai_rest_hello_world.dto.ChatRequest;
import com.example.__spring_ai_rest_hello_world.dto.ChatResponse;
import com.example.__spring_ai_rest_hello_world.service.ChatService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * REST Controller for chat functionality using Spring AI and GroQ LLM
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * Simple chat endpoint - sends a message and gets a response
     * 
     * POST /api/chat
     * Body: { "message": "Hello, how are you?" }
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        try {
            ChatResponse response = chatService.chat(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ChatResponse errorResponse = ChatResponse.builder()
                    .success(false)
                    .error(e.getMessage())
                    .model(chatService.getDefaultModel())
                    .build();
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * Streaming chat endpoint - sends a message and gets a streaming response
     * 
     * POST /api/chat/stream
     * Body: { "message": "Tell me a story" }
     * Returns: Plain text streaming response
     */
    @PostMapping(value = "/stream", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> chatStream(@RequestBody ChatRequest request) {
        return chatService.chatStream(request);
    }

    /**
     * Health check endpoint
     * 
     * GET /api/chat/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Chat service is running with GroQ LLM");
    }

    /**
     * Get the default model being used
     * 
     * GET /api/chat/model
     */
    @GetMapping("/model")
    public ResponseEntity<String> getModel() {
        return ResponseEntity.ok(chatService.getDefaultModel());
    }
}

