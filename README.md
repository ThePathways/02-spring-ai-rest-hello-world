# 02-spring-ai-rest-hello-world

A REST-based chat application built with Spring AI and GroQ LLM (Large Language Model).

## Overview

This project demonstrates how to build a simple chat REST API using Spring AI framework with Groq as the underlying LLM provider. It provides both synchronous and streaming chat endpoints.

## Technology Stack

- **Spring Boot** 4.0.3
- **Spring AI** 2.0.0-M2
- **Java** 21
- **Groq LLM** (OpenAI-compatible API)
- **Lombok**

## Project Structure

```
src/main/java/com/example/__spring_ai_rest_hello_world/
├── Application.java                 # Main Spring Boot application
├── config/
│   └── GroqChatModelConfig.java     # ChatModel configuration
├── controller/
│   └── ChatController.java          # REST endpoints
├── dto/
│   ├── ChatRequest.java             # Request DTO
│   └── ChatResponse.java            # Response DTO
└── service/
    └── ChatService.java             # Chat business logic
```

## Configuration

The application is configured via `src/main/resources/application.properties`:

```properties
spring.application.name=02-spring-ai-rest-hello-world

# GroQ Configuration (using OpenAI-compatible API)
spring.ai.openai.base-url=https://api.groq.com/openai
spring.ai.openai.api-key=your-api-key-here

# GroQ Model
spring.ai.chat.model=llama-3.3-70b-versatile

# Optional: Configure chat options
spring.ai.chat.options.temperature=0.7
spring.ai.chat.options.max-tokens=2048
```

**Note:** Replace `spring.ai.openai.api-key` with your own Groq API key. Get one at [groq.com](https://groq.com).

## API Endpoints

### 1. Simple Chat (POST)

Send a message and get a response.

```bash
curl -s -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Hello! How are you?"}'
```

**Response:**
```json
{
  "response": "Hello. I'm doing well, thanks for asking...",
  "model": "llama-3.3-70b-versatile",
  "tokensUsed": 121,
  "success": true,
  "error": null
}
```

### 2. Streaming Chat (POST)

Send a message and get a streaming response.

```bash
curl -N -X POST http://localhost:8080/api/chat/stream \
  -H "Content-Type: application/json" \
  -d '{"message": "Tell me a short story"}'
```

This endpoint returns plain text streaming response (user-friendly output without SSE "data:" prefix).

### 3. Health Check (GET)

Check if the service is running.

```bash
curl http://localhost:8080/api/chat/health
```

**Response:**
```
Chat service is running with GroQ LLM
```

### 4. Get Model (GET)

Get the default model being used.

```bash
curl http://localhost:8080/api/chat/model
```

**Response:**
```
llama-3.3-70b-versatile
```

## Running the Application

### Prerequisites

- Java 21 or higher
- Maven
- Groq API key

### Build and Run

```bash
# Build the project
./mvnw clean package

# Run the application
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`.

## Request/Response DTOs

### ChatRequest

```json
{
  "message": "Your message here",
  "model": "optional-model-override",
  "temperature": 0.7,
  "messages": [
    {"role": "user", "content": "Previous message"},
    {"role": "assistant", "content": "Previous response"}
  ]
}
```

### ChatResponse

```json
{
  "response": "AI response text",
  "model": "llama-3.3-70b-versatile",
  "tokensUsed": 150,
  "success": true,
  "error": null
}
```

## Features

- **Synchronous Chat**: Get complete responses in one go
- **Streaming Chat**: Stream tokens as they are generated for real-time output
- **Configurable Model**: Use different Groq models
- **Temperature Control**: Adjust response creativity
- **Chat History Support**: Include previous messages for context

## Available Groq Models

Some popular Groq models you can use:

- `llama-3.3-70b-versatile` (default)
- `llama-3.1-70b-versatile`
- `llama-3.1-8b-instant`
- `mixtral-8x7b-32768`

To use a different model, either:
1. Update `spring.ai.chat.model` in `application.properties`
2. Pass the model in the request body: `"model": "llama-3.1-8b-instant"`

## License

This project is under MIT License.

