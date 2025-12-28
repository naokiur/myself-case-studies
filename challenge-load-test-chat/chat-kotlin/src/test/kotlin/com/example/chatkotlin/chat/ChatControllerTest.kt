package com.example.chatkotlin.chat

import com.example.chatkotlin.message.MessageEntity
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.Instant
import java.util.*

@WebMvcTest(controllers = [ChatController::class])
@Import(ApiExceptionHandler::class)
class ChatControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
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

    @Test
    fun listMessages_returnsOk() {
        val chatId = UUID.randomUUID()
        val list = listOf(
            MessageEntity(chatId = chatId, sender = "a", content = "c1").copy(id = UUID.randomUUID()),
            MessageEntity(chatId = chatId, sender = "b", content = "c2").copy(id = UUID.randomUUID()),
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
        whenever(chatService.getMessage(chatId, messageId)).thenThrow(NoSuchElementException("not found"))

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
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sender\":\"s\",\"content\":\"c\"}")
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(messageId.toString()))
            .andExpect(jsonPath("$.chatId").value(chatId.toString()))
    }

    @Test
    fun updateMessage_returnsOk_forBothPaths() {
        val chatId = UUID.randomUUID()
        val messageId = UUID.randomUUID()
        val updated = MessageEntity(id = messageId, chatId = chatId, sender = "ns", content = "nc")
        whenever(chatService.updateMessage(chatId, messageId, "nc", "ns")).thenReturn(updated)

        // Proper path
        mockMvc.perform(
            put("/api/chat/{chatId}/message/{messageId}", chatId, messageId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sender\":\"ns\",\"content\":\"nc\"}")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(messageId.toString()))

        // Typo path variant without slash
        mockMvc.perform(
            put("/api/chat/{chatId}message/{messageId}", chatId, messageId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sender\":\"ns\",\"content\":\"nc\"}")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(messageId.toString()))
    }
}
