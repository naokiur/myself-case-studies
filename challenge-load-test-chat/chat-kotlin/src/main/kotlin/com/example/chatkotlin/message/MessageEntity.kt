package com.example.chatkotlin.message

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.util.*

@Table("Messages")
data class MessageEntity(
    @Id
    val id: UUID? = null,
    @Column("chat_id")
    var chatId: UUID,
    var sender: String,
    var content: String,
    @Column("created_at")
    val createdAt: Instant = Instant.now(),
    @Column("updated_at")
    val updatedAt: Instant = Instant.now(),
)
