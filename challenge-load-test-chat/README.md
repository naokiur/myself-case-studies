chat-kotlin (Spring Boot Kotlin)

This module provides a simple Chat REST API with PostgreSQL, built using Spring Boot (latest), Kotlin 2.1, and Java 25.

Modules
- chat-kotlin: Spring Boot Kotlin application exposing REST endpoints
- docker-compose.yml: Local PostgreSQL + pgAdmin4 setup

Requirements
- JDK 25
- Docker + Docker Compose
- IntelliJ IDEA (recommended)

How to run (local DB via Docker)
1. Start PostgreSQL and pgAdmin4:
   - cd challenge-load-test-chat
   - docker compose up -d
   - PostgreSQL: localhost:5432 (db: chatdb, user: chat, password: chatpass)
   - pgAdmin4: http://localhost:5050 (email: admin@local, password: admin)
   - In pgAdmin4, register a new server with hostname postgres, user chat, password chatpass.

2. Run the app:
   - cd challenge-load-test-chat/chat-kotlin
   - ./gradlew bootRun
   - The app starts on http://localhost:8080

REST API (base path: /api)
- POST /api/chat (body: {"title":"Optional title"}) -> 201 Created
- GET /api/chat/{id}
- GET /api/chat/{chat_id}/messages
- GET /api/chat/{chat_id}/messages/{message_id}
- POST /api/chat/{chat_id}/message (body: {"sender":"alice","content":"hello"}) -> 201 Created
- PUT /api/chat/{chat_id}/message/{message_id} (body: {"sender":"alice","content":"updated"})
- Also accepted: PUT /api/chat/{chat_id}message/{message_id}

Database schema
- On startup, Spring runs src/main/resources/schema.sql to ensure tables exist (Chats, Messages).
- Messages.chat_id references Chats.id (ON DELETE CASCADE).

Configuration
- Default configuration in src/main/resources/application.yml
- Overridable env vars: SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME, SPRING_DATASOURCE_PASSWORD, SERVER_PORT

Build
- cd challenge-load-test-chat/chat-kotlin && ./gradlew build

Notes
- Kotlin 2.1 + Spring Boot 3.4.x
- Java toolchain: 25 (Kotlin jvmTarget 21)
