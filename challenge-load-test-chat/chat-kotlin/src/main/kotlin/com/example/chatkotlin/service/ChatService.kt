package com.example.chatkotlin.service

import com.example.chatkotlin.domain.Chat
import com.example.chatkotlin.domain.Message
import com.example.chatkotlin.repository.ChatRepository
import com.example.chatkotlin.repository.MessageRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatService(
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository,
) {
    fun createChat(title: String?): Chat = chatRepository.save(Chat(title = title))

    fun getChat(id: UUID): Chat? = chatRepository.findByIdOrNull(id)

    fun createMessage(chatId: UUID, sender: String, content: String): Message {
        // Ensure chat exists
        chatRepository.findByIdOrNull(chatId) ?: throw NoSuchElementException("Chat not found: $chatId")
        return messageRepository.save(Message(chatId = chatId, sender = sender, content = content))
    }

    fun listMessages(chatId: UUID): List<Message> {
        // Ensure chat exists
        chatRepository.findByIdOrNull(chatId) ?: throw NoSuchElementException("Chat not found: $chatId")
        return messageRepository.findAllByChatId(chatId)
    }

    fun getMessage(chatId: UUID, messageId: UUID): Message {
        val msg = messageRepository.findByIdOrNull(messageId) ?: throw NoSuchElementException("Message not found: $messageId")
        if (msg.chatId != chatId) throw NoSuchElementException("Message $messageId does not belong to chat $chatId")
        return msg
    }

    fun updateMessage(chatId: UUID, messageId: UUID, content: String, sender: String?): Message {
        val msg = getMessage(chatId, messageId)
        val newSender = sender ?: msg.sender
        val updated = msg.copy(sender = newSender, content = content)
        return messageRepository.save(updated)
    }
}
