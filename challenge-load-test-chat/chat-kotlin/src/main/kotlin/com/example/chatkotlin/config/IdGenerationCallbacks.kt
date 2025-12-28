package com.example.chatkotlin.config

import com.example.chatkotlin.domain.Chat
import com.example.chatkotlin.domain.Message
import org.springframework.context.annotation.Configuration
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback
import java.util.*

@Configuration
class ChatIdGenerationCallback : BeforeConvertCallback<Chat> {
    override fun onBeforeConvert(aggregate: Chat): Chat =
        if (aggregate.id == null) aggregate.copy(id = UUID.randomUUID()) else aggregate
}

@Configuration
class MessageIdGenerationCallback : BeforeConvertCallback<Message> {
    override fun onBeforeConvert(aggregate: Message): Message =
        if (aggregate.id == null) aggregate.copy(id = UUID.randomUUID()) else aggregate
}
