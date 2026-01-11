package com.example.chatkotlin.message

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

data class CreateMessageRequest(val sender: String, val content: String)
data class UpdateMessageRequest(val sender: String?, val content: String)
data class MessageResponse(
    val id: UUID,
    val chatId: UUID,
    val sender: String,
    val content: String,
    val createdAt: String?,
    val updatedAt: String?,
) {
    companion object {
        fun from(m: MessageEntity) = MessageResponse(
            id = requireNotNull(m.id),
            chatId = m.chatId,
            sender = m.sender,
            content = m.content,
            createdAt = m.createdAt.toString(),
            updatedAt = m.updatedAt.toString(),
        )
    }
}

@RestController
@RequestMapping("/api/chat/{chatId}")
class MessageController(
    private val messageService: MessageService,
) {

    @GetMapping("/messages")
    fun listMessages(@PathVariable chatId: UUID): ResponseEntity<List<MessageResponse>> {
        val list = messageService.listMessages(chatId).map { MessageResponse.from(it) }
        return ResponseEntity.ok(list)
    }

    @GetMapping("/messages/{messageId}")
    fun getMessage(@PathVariable chatId: UUID, @PathVariable messageId: UUID): ResponseEntity<MessageResponse> =
        ResponseEntity.ok(MessageResponse.from(messageService.getMessage(chatId, messageId)))

    @PostMapping("/message")
    fun createMessage(
        @PathVariable chatId: UUID,
        @RequestBody req: CreateMessageRequest
    ): ResponseEntity<MessageResponse> {
        val created = messageService.createMessage(chatId, req.sender, req.content)
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from(created))
    }

    // Proper path
    @PutMapping("/messages/{messageId}")
    fun updateMessage(
        @PathVariable chatId: UUID,
        @PathVariable messageId: UUID,
        @RequestBody req: UpdateMessageRequest
    ): ResponseEntity<MessageResponse> {
        val updated = messageService.updateMessage(chatId, messageId, req.content, req.sender)
        return ResponseEntity.ok(MessageResponse.from(updated))
    }
}