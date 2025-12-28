package com.example.chatkotlin.repository

import com.example.chatkotlin.domain.Message
import org.springframework.data.repository.ListCrudRepository
import java.util.*

interface MessageRepository : ListCrudRepository<Message, UUID> {
    fun findAllByChatId(chatId: UUID): List<Message>
}
