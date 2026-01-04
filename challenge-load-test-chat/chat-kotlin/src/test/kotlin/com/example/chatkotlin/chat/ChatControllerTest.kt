package com.example.chatkotlin.chat

import com.example.chatkotlin.handler.ApiExceptionHandler
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant
import java.util.UUID

@WebMvcTest(controllers = [ChatController::class])
@Import(ApiExceptionHandler::class, ChatControllerTest.TestConfig::class)
class ChatControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var chatService: ChatService

    @Test
    fun getChat_found_returnsOk() {
        val id = UUID.randomUUID()
        val created = Instant.parse("2024-01-01T00:00:00Z")
        val updated = Instant.parse("2024-01-02T00:00:00Z")
        val entity = ChatEntity(id = id, title = "hello", createdAt = created, updatedAt = updated)
        whenever(chatService.getChat(id)).thenReturn(entity)

        mockMvc.perform(get("/api/chat/{id}", id))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.title").value("hello"))
            .andExpect(jsonPath("$.createdAt").value(created.toString()))
            .andExpect(jsonPath("$.updatedAt").value(updated.toString()))
    }

    @Test
    fun getChat_notFound_returns404() {
        val id = UUID.randomUUID()
        whenever(chatService.getChat(id)).thenReturn(null)

        mockMvc.perform(get("/api/chat/{id}", id))
            .andExpect(status().isNotFound)
    }

    @Test
    fun createChat_returnsCreated() {
        val id = UUID.randomUUID()
        val entity = ChatEntity(id = id, title = "new")
        whenever(chatService.createChat(any())).thenReturn(entity)

        mockMvc.perform(
            post("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" + "\"title\":\"new\"" + "}")
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.title").value("new"))
    }

    @TestConfiguration
    class TestConfig {
        @Bean
        fun chatService(): ChatService = mock()
    }
}
