package com.example.chatkotlin.config

import com.example.chatkotlin.chat.ChatEntity
import com.example.chatkotlin.message.MessageEntity
import org.springframework.context.annotation.Configuration
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback
import java.util.*

@Configuration
class ChatIdGenerationCallback : BeforeConvertCallback<ChatEntity> {
    override fun onBeforeConvert(aggregate: ChatEntity): ChatEntity =
        if (aggregate.id == null) aggregate.copy(id = UUID.randomUUID()) else aggregate
}

@Configuration
class MessageIdGenerationCallback : BeforeConvertCallback<MessageEntity> {
    override fun onBeforeConvert(aggregate: MessageEntity): MessageEntity =
        if (aggregate.id == null) aggregate.copy(id = UUID.randomUUID()) else aggregate
}
