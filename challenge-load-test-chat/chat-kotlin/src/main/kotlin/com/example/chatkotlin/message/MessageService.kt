package com.example.chatkotlin.message

import com.example.chatkotlin.chat.ChatRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MessageService(
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository
) {
    fun createMessage(chatId: UUID, sender: String, content: String): MessageEntity {
        chatRepository.findByIdOrNull(chatId) ?: throw NoSuchElementException("Chat not found: $chatId")

        return messageRepository.save(
            MessageEntity(chatId = chatId, sender = sender, content = content)
        )
    }

    fun listMessages(chatId: UUID): List<MessageEntity> {
        return messageRepository.findAllByChatId(chatId)
    }

    fun getMessage(chatId: UUID, messageId: UUID): MessageEntity {
        val message = messageRepository.findById(messageId)
            .orElseThrow { NoSuchElementException("Message not found: $messageId") }
        if (message.chatId != chatId) throw NoSuchElementException("Message $messageId does not belong to chat $chatId")

        return message
    }

    fun updateMessage(chatId: UUID, messageId: UUID, content: String, sender: String?): MessageEntity {
        val message = getMessage(chatId, messageId)
        val newSender = sender ?: message.sender
        return messageRepository.save(message.copy(sender = newSender, content = content))
    }
}
