package com.example.chatkotlin.service

import com.example.chatkotlin.domain.Chat
import com.example.chatkotlin.domain.Message
import com.example.chatkotlin.repository.ChatRepository
import com.example.chatkotlin.repository.MessageRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ChatService(
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository,
) {
    @Transactional
    fun createChat(title: String?): Chat = chatRepository.save(Chat(title = title))

    @Transactional(readOnly = true)
    fun getChat(id: UUID): Chat? = chatRepository.findByIdOrNull(id)

    @Transactional
    fun createMessage(chatId: UUID, sender: String, content: String): Message {
        val chat = chatRepository.findByIdOrNull(chatId) ?: throw NoSuchElementException("Chat not found: $chatId")
        return messageRepository.save(Message(chat = chat, sender = sender, content = content))
    }

    @Transactional(readOnly = true)
    fun listMessages(chatId: UUID): List<Message> {
        // Ensure chat exists
        chatRepository.findByIdOrNull(chatId) ?: throw NoSuchElementException("Chat not found: $chatId")
        return messageRepository.findAllByChat_Id(chatId)
    }

    @Transactional(readOnly = true)
    fun getMessage(chatId: UUID, messageId: UUID): Message {
        val msg = messageRepository.findByIdOrNull(messageId) ?: throw NoSuchElementException("Message not found: $messageId")
        if (msg.chat.id != chatId) throw NoSuchElementException("Message $messageId does not belong to chat $chatId")
        return msg
    }

    @Transactional
    fun updateMessage(chatId: UUID, messageId: UUID, content: String, sender: String?): Message {
        val msg = getMessage(chatId, messageId)
        sender?.let { msg.sender = it }
        msg.content = content
        return messageRepository.save(msg)
    }
}
