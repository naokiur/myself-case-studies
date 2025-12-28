package com.example.chatkotlin.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
import java.util.*

@Table("Messages")
data class Message(
    @Id
    val id: UUID = UUID.randomUUID(),
    @Column("chat_id")
    var chatId: UUID,
    var sender: String,
    var content: String,
    @Column("created_at")
    val createdAt: OffsetDateTime? = null,
    @Column("updated_at")
    val updatedAt: OffsetDateTime? = null,
)
