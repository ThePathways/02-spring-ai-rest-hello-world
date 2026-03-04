package com.example.__spring_ai_rest_hello_world.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for GroQ ChatModel using OpenAI-compatible API
 */
@Configuration
public class GroqChatModelConfig {

    @Value("${spring.ai.openai.base-url}")
    private String baseUrl;

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.chat.model:llama-3.1-70b-versatile}")
    private String defaultModel;

    @Value("${spring.ai.chat.options.temperature:0.7}")
    private Double temperature;

    @Value("${spring.ai.chat.options.max-tokens:2048}")
    private Integer maxTokens;

    @Bean
    public ChatModel chatModel() {
        OpenAiApi openAiApi = OpenAiApi.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .build();
        
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model(defaultModel)
                .temperature(temperature)
                .maxTokens(maxTokens)
                .build();
        
        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(options)
                .build();
    }

    @Bean
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }
}

