package com.example.chatkotlin.chat

import com.example.chatkotlin.message.MessageEntity
import com.example.chatkotlin.message.MessageRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatService(
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository,
) {
    fun createChat(title: String?): ChatEntity = chatRepository.save(ChatEntity(title = title))

    fun getChat(id: UUID): ChatEntity? = chatRepository.findByIdOrNull(id)

    fun createMessage(chatId: UUID, sender: String, content: String): MessageEntity {
        // Ensure chat exists
        chatRepository.findByIdOrNull(chatId) ?: throw NoSuchElementException("Chat not found: $chatId")
        return messageRepository.save(MessageEntity(chatId = chatId, sender = sender, content = content))
    }

    fun listMessages(chatId: UUID): List<MessageEntity> {
        // Ensure chat exists
        chatRepository.findByIdOrNull(chatId) ?: throw NoSuchElementException("Chat not found: $chatId")
        return messageRepository.findAllByChatId(chatId)
    }

    fun getMessage(chatId: UUID, messageId: UUID): MessageEntity {
        val msg = messageRepository.findByIdOrNull(messageId) ?: throw NoSuchElementException("Message not found: $messageId")
        if (msg.chatId != chatId) throw NoSuchElementException("Message $messageId does not belong to chat $chatId")
        return msg
    }

    fun updateMessage(chatId: UUID, messageId: UUID, content: String, sender: String?): MessageEntity {
        val msg = getMessage(chatId, messageId)
        val newSender = sender ?: msg.sender
        val updated = msg.copy(sender = newSender, content = content)
        return messageRepository.save(updated)
    }
}
