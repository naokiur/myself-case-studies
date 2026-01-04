package com.example.chatkotlin.chat

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ChatService(
    private val chatRepository: ChatRepository
) {
    fun createChat(title: String?): ChatEntity = chatRepository.save(ChatEntity(title = title))

    fun getChat(id: UUID): ChatEntity? = chatRepository.findByIdOrNull(id)
}
