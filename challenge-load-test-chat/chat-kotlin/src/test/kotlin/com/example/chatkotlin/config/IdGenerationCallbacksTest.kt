package com.example.chatkotlin.config

import com.example.chatkotlin.chat.ChatEntity
import com.example.chatkotlin.message.MessageEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class IdGenerationCallbacksTest {

    @Test
    fun chatCallback_assignsIdWhenNull_andKeepsWhenPresent() {
        val cb = ChatIdGenerationCallback()

        val withNull = ChatEntity(title = "t")
        val after = cb.onBeforeConvert(withNull)
        assertNotNull(after.id)

        val existingId = UUID.randomUUID()
        val withId = ChatEntity(id = existingId, title = "t2")
        val after2 = cb.onBeforeConvert(withId)
        assertEquals(existingId, after2.id)
    }

    @Test
    fun messageCallback_assignsIdWhenNull_andKeepsWhenPresent() {
        val cb = MessageIdGenerationCallback()
        val chatId = UUID.randomUUID()

        val withNull = MessageEntity(chatId = chatId, sender = "s", content = "c")
        val after = cb.onBeforeConvert(withNull)
        assertNotNull(after.id)

        val existingId = UUID.randomUUID()
        val withId = MessageEntity(id = existingId, chatId = chatId, sender = "s2", content = "c2")
        val after2 = cb.onBeforeConvert(withId)
        assertEquals(existingId, after2.id)
    }
}
