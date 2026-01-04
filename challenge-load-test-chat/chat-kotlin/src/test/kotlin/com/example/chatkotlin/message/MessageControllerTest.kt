package com.example.chatkotlin.message

import com.example.chatkotlin.chat.ChatService
import com.example.chatkotlin.handler.ApiExceptionHandler
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.UUID

@WebMvcTest(controllers = [MessageController::class])
@Import(ApiExceptionHandler::class, MessageControllerTest.TestConfig::class)
class MessageControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var chatService: ChatService

    @Test
    fun listMessages_returnsOk() {
        val chatId = UUID.randomUUID()
        val list = listOf(
            MessageEntity(
                chatId = chatId,
                sender = "a",
                content = "c1"
            ).copy(id = UUID.randomUUID()),
            MessageEntity(
                chatId = chatId,
                sender = "b",
                content = "c2"
            ).copy(id = UUID.randomUUID()),
        )
        whenever(chatService.listMessages(chatId)).thenReturn(list)

        mockMvc.perform(get("/api/chat/{chatId}/messages", chatId))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Int>(2)))
            .andExpect(jsonPath("$[0].chatId").value(chatId.toString()))
            .andExpect(jsonPath("$[1].chatId").value(chatId.toString()))
    }

    @Test
    fun getMessage_found_returnsOk() {
        val chatId = UUID.randomUUID()
        val messageId = UUID.randomUUID()
        val msg = MessageEntity(id = messageId, chatId = chatId, sender = "s", content = "c")
        whenever(chatService.getMessage(chatId, messageId)).thenReturn(msg)

        mockMvc.perform(get("/api/chat/{chatId}/messages/{messageId}", chatId, messageId))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(messageId.toString()))
            .andExpect(jsonPath("$.chatId").value(chatId.toString()))
            .andExpect(jsonPath("$.sender").value("s"))
            .andExpect(jsonPath("$.content").value("c"))
    }

    @Test
    fun getMessage_notFound_returns404() {
        val chatId = UUID.randomUUID()
        val messageId = UUID.randomUUID()
        whenever(
            chatService.getMessage(
                chatId,
                messageId
            )
        ).thenThrow(NoSuchElementException("not found"))

        mockMvc.perform(get("/api/chat/{chatId}/messages/{messageId}", chatId, messageId))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.error").value("not found"))
    }

    @Test
    fun createMessage_returnsCreated() {
        val chatId = UUID.randomUUID()
        val messageId = UUID.randomUUID()
        val saved = MessageEntity(id = messageId, chatId = chatId, sender = "s", content = "c")
        whenever(chatService.createMessage(chatId, "s", "c")).thenReturn(saved)

        mockMvc.perform(
            post("/api/chat/{chatId}/message", chatId)
                .contentType("application/json")
                .content("{\"sender\":\"s\",\"content\":\"c\"}")
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(saved.id.toString()))
            .andExpect(jsonPath("$.chatId").value(chatId.toString()))
            .andExpect(jsonPath("$.sender").value("s"))
            .andExpect(jsonPath("$.content").value("c"))
    }

    @Test
    fun updateMessage_returnsOk() {
        val chatId = UUID.randomUUID()
        val messageId = UUID.randomUUID()
        val updated = MessageEntity(id = messageId, chatId = chatId, sender = "ns", content = "nc")
        whenever(chatService.updateMessage(chatId, messageId, "nc", "ns")).thenReturn(updated)

        mockMvc.perform(
            put("/api/chat/{chatId}/messages/{messageId}", chatId, messageId)
                .contentType("application/json")
                .content("{\"sender\":\"ns\",\"content\":\"nc\"}")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(messageId.toString()))
            .andExpect(jsonPath("$.chatId").value(chatId.toString()))
            .andExpect(jsonPath("$.sender").value("ns"))
            .andExpect(jsonPath("$.content").value("nc"))
    }

    @TestConfiguration
    class TestConfig {
        @Bean
        fun chatService(): ChatService = mock()
    }
}