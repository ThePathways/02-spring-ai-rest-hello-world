package com.example.__spring_ai_rest_hello_world.service;

import com.example.__spring_ai_rest_hello_world.dto.ChatRequest;
import com.example.__spring_ai_rest_hello_world.dto.ChatResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Service class for handling chat operations with GroQ LLM
 */
@Service
public class ChatService {

    private final ChatClient chatClient;

    @Value("${spring.ai.chat.model:llama-3.1-70b-versatile}")
    private String defaultModel;

    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * Send a chat message and get a response
     * 
     * @param request the chat request containing the message
     * @return chat response with AI reply
     */
    public ChatResponse chat(ChatRequest request) {
        // Build the prompt with optional previous messages
        String prompt = request.getMessage();
        
        if (request.getMessages() != null && !request.getMessages().isEmpty()) {
            // Use the chat client with history for more complex conversations
            StringBuilder fullPrompt = new StringBuilder();
            for (ChatRequest.ChatMessage msg : request.getMessages()) {
                fullPrompt.append(msg.getRole())
                          .append(": ")
                          .append(msg.getContent())
                          .append("\n");
            }
            fullPrompt.append("user: ").append(request.getMessage());
            prompt = fullPrompt.toString();
        }

        // Build chat options with explicit model
        String modelToUse = request.getModel() != null ? request.getModel() : defaultModel;
        Double temperature = request.getTemperature() != null ? request.getTemperature() : 0.7;

        org.springframework.ai.chat.model.ChatResponse response = chatClient
                .prompt()
                .user(prompt)
                .system(s -> s.text("You are a helpful assistant. Model: " + modelToUse + ", Temperature: " + temperature))
                .call()
                .chatResponse();

        String responseText = response.getResult().getOutput().getText();
        Integer tokensUsed = response.getMetadata().getUsage() != null 
                ? response.getMetadata().getUsage().getTotalTokens() 
                : null;

        return ChatResponse.builder()
                .response(responseText)
                .model(modelToUse)
                .tokensUsed(tokensUsed)
                .success(true)
                .build();
    }

    /**
     * Send a chat message and get a streaming response
     * 
     * @param request the chat request containing the message
     * @return flux of streaming response content
     */
    public Flux<String> chatStream(ChatRequest request) {
        String modelToUse = request.getModel() != null ? request.getModel() : defaultModel;
        Double temperature = request.getTemperature() != null ? request.getTemperature() : 0.7;
        
        return chatClient
                .prompt()
                .user(request.getMessage())
                .system(s -> s.text("You are a helpful assistant. Model: " + modelToUse + ", Temperature: " + temperature))
                .stream()
                .content();
    }

    /**
     * Get the default model being used
     * 
     * @return the model name
     */
    public String getDefaultModel() {
        return defaultModel;
    }
}

