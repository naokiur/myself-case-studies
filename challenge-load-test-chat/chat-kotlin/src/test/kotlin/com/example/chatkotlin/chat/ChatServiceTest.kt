package com.example.chatkotlin.chat

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.util.Optional
import java.util.UUID

class ChatServiceTest {
    private lateinit var chatRepository: ChatRepository
    private lateinit var service: ChatService

    @BeforeEach
    fun setup() {
        chatRepository = mock()
        service = ChatService(chatRepository)
    }

    @Test
    fun createChat_savesAndReturns() {
        val saved = ChatEntity(title = "hello")
        whenever(chatRepository.save(any())).thenReturn(saved)

        val result = service.createChat("hello")

        Assertions.assertEquals(saved, result)
        verify(chatRepository).save(any())
    }

    @Test
    fun createChat_passesEntityWithNullId_ToSave() {
        val saved = ChatEntity(title = "t")
        whenever(chatRepository.save(any())).thenReturn(saved)

        service.createChat("t")

        val captor = argumentCaptor<ChatEntity>()
        verify(chatRepository).save(captor.capture())
        Assertions.assertNull(captor.firstValue.id, "New Chat must have null id so that INSERT is executed, not UPDATE")
    }

    @Test
    fun getChat_returnsEntityOrNull() {
        val id = UUID.randomUUID()
        val chatEntity = ChatEntity(id = id, title = "t")
        whenever(chatRepository.findById(id)).thenReturn(Optional.of(chatEntity))

        val found = service.getChat(id)
        Assertions.assertNotNull(found)
        Assertions.assertEquals(chatEntity, found)
    }
}