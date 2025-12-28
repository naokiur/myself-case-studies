package com.example.chatkotlin.service

import com.example.chatkotlin.chat.ChatEntity
import com.example.chatkotlin.message.MessageEntity
import com.example.chatkotlin.chat.ChatRepository
import com.example.chatkotlin.chat.ChatService
import com.example.chatkotlin.message.MessageRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Optional
import java.util.UUID

class ChatServiceTest {
    private lateinit var chatRepository: ChatRepository
    private lateinit var messageRepository: MessageRepository
    private lateinit var service: ChatService

    @BeforeEach
    fun setup() {
        chatRepository = mock()
        messageRepository = mock()
        service = ChatService(chatRepository, messageRepository)
    }

    @Test
    fun createChat_savesAndReturns() {
        val saved = ChatEntity(title = "hello")
        whenever(chatRepository.save(any())).thenReturn(saved)

        val result = service.createChat("hello")

        assertEquals(saved, result)
        verify(chatRepository).save(any())
    }

    @Test
    fun createChat_passesEntityWithNullId_ToSave() {
        val saved = ChatEntity(title = "t")
        whenever(chatRepository.save(any())).thenReturn(saved)

        service.createChat("t")

        val captor = argumentCaptor<ChatEntity>()
        verify(chatRepository).save(captor.capture())
        assertNull(captor.firstValue.id, "New Chat must have null id so that INSERT is executed, not UPDATE")
    }

    @Test
    fun getChat_returnsEntityOrNull() {
        val id = UUID.randomUUID()
        val chatEntity = ChatEntity(id = id, title = "t")
        whenever(chatRepository.findById(id)).thenReturn(Optional.of(chatEntity))

        val found = service.getChat(id)
        assertNotNull(found)
        assertEquals(chatEntity, found)
    }

    @Test
    fun createMessage_throwsWhenChatMissing() {
        val chatId = UUID.randomUUID()
        whenever(chatRepository.findById(chatId)).thenReturn(Optional.empty())

        assertThrows<NoSuchElementException> {
            service.createMessage(chatId, sender = "a", content = "c")
        }
    }

    @Test
    fun createMessage_savesWhenChatExists() {
        val chatId = UUID.randomUUID()
        whenever(chatRepository.findById(chatId)).thenReturn(Optional.of(ChatEntity(id = chatId)))
        val toSave = MessageEntity(chatId = chatId, sender = "a", content = "c")
        val saved = toSave.copy(id = UUID.randomUUID())
        whenever(messageRepository.save(any())).thenReturn(saved)

        val result = service.createMessage(chatId, sender = "a", content = "c")
        assertEquals(saved, result)
        verify(messageRepository).save(any())
    }

    @Test
    fun listMessages_checksChatExistence() {
        val chatId = UUID.randomUUID()
        whenever(chatRepository.findById(chatId)).thenReturn(Optional.empty())

        assertThrows<NoSuchElementException> { service.listMessages(chatId) }
    }

    @Test
    fun listMessages_returnsList() {
        val chatId = UUID.randomUUID()
        whenever(chatRepository.findById(chatId)).thenReturn(Optional.of(ChatEntity(id = chatId)))
        val list = listOf(
            MessageEntity(chatId = chatId, sender = "s1", content = "c1"),
            MessageEntity(chatId = chatId, sender = "s2", content = "c2"),
        )
        whenever(messageRepository.findAllByChatId(chatId)).thenReturn(list)

        val result = service.listMessages(chatId)
        assertEquals(list, result)
    }

    @Test
    fun getMessage_notFound() {
        val chatId = UUID.randomUUID()
        val msgId = UUID.randomUUID()
        whenever(messageRepository.findById(msgId)).thenReturn(Optional.empty())
        assertThrows<NoSuchElementException> { service.getMessage(chatId, msgId) }
    }

    @Test
    fun getMessage_mismatchedChat() {
        val chatId = UUID.randomUUID()
        val otherChatId = UUID.randomUUID()
        val msgId = UUID.randomUUID()
        val msg = MessageEntity(id = msgId, chatId = otherChatId, sender = "a", content = "b")
        whenever(messageRepository.findById(msgId)).thenReturn(Optional.of(msg))
        assertThrows<NoSuchElementException> { service.getMessage(chatId, msgId) }
    }

    @Test
    fun updateMessage_updatesContentAndOptionalSender() {
        val chatId = UUID.randomUUID()
        val msgId = UUID.randomUUID()
        val existing = MessageEntity(id = msgId, chatId = chatId, sender = "old", content = "oldC")
        whenever(messageRepository.findById(msgId)).thenReturn(Optional.of(existing))
        val saved = existing.copy(sender = "new", content = "NEW")
        whenever(messageRepository.save(any())).thenReturn(saved)

        val result = service.updateMessage(chatId, msgId, content = "NEW", sender = "new")
        assertEquals("new", result.sender)
        assertEquals("NEW", result.content)
        verify(messageRepository).save(any())
    }

    @Test
    fun updateMessage_keepsSenderWhenNull() {
        val chatId = UUID.randomUUID()
        val msgId = UUID.randomUUID()
        val existing = MessageEntity(id = msgId, chatId = chatId, sender = "keep", content = "oldC")
        whenever(messageRepository.findById(msgId)).thenReturn(Optional.of(existing))
        val saved = existing.copy(content = "NEW")
        whenever(messageRepository.save(any())).thenReturn(saved)

        val result = service.updateMessage(chatId, msgId, content = "NEW", sender = null)
        assertEquals("keep", result.sender)
        assertEquals("NEW", result.content)
    }
}
