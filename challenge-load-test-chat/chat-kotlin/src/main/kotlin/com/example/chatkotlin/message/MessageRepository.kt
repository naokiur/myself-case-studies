package com.example.chatkotlin.message

import org.springframework.data.repository.ListCrudRepository
import java.util.*

interface MessageRepository : ListCrudRepository<MessageEntity, UUID> {
    fun findAllByChatId(chatId: UUID): List<MessageEntity>
}
