package com.example.chatkotlin.repository

import com.example.chatkotlin.domain.Message
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MessageRepository : JpaRepository<Message, UUID> {
    fun findAllByChat_Id(chatId: UUID): List<Message>
}
