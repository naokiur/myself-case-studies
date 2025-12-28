package com.example.chatkotlin.chat

import com.example.chatkotlin.message.MessageEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

data class CreateChatRequest(val title: String?)
data class ChatResponse(val id: UUID, val title: String?, val createdAt: String?, val updatedAt: String?) {
    companion object {
        fun from(chatEntity: ChatEntity) = ChatResponse(
            id = requireNotNull(chatEntity.id),
            title = chatEntity.title,
            createdAt = chatEntity.createdAt?.toString(),
            updatedAt = chatEntity.updatedAt?.toString(),
        )
    }
}

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
            createdAt = m.createdAt?.toString(),
            updatedAt = m.updatedAt?.toString(),
        )
    }
}

@RestController
@RequestMapping("/api")
class ChatController(private val chatService: ChatService) {

    @GetMapping("/chat/{id}")
    fun getChat(@PathVariable id: UUID): ResponseEntity<ChatResponse> {
        val chat = chatService.getChat(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(ChatResponse.from(chat))
    }

    @PostMapping("/chat")
    fun createChat(@RequestBody req: CreateChatRequest): ResponseEntity<ChatResponse> {
        val created = chatService.createChat(req.title)
        return ResponseEntity.status(HttpStatus.CREATED).body(ChatResponse.from(created))
    }

    @GetMapping("/chat/{chatId}/messages")
    fun listMessages(@PathVariable chatId: UUID): ResponseEntity<List<MessageResponse>> {
        val list = chatService.listMessages(chatId).map { MessageResponse.from(it) }
        return ResponseEntity.ok(list)
    }

    @GetMapping("/chat/{chatId}/messages/{messageId}")
    fun getMessage(@PathVariable chatId: UUID, @PathVariable messageId: UUID): ResponseEntity<MessageResponse> =
        ResponseEntity.ok(MessageResponse.from(chatService.getMessage(chatId, messageId)))

    @PostMapping("/chat/{chatId}/message")
    fun createMessage(@PathVariable chatId: UUID, @RequestBody req: CreateMessageRequest): ResponseEntity<MessageResponse> {
        val created = chatService.createMessage(chatId, req.sender, req.content)
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from(created))
    }

    // Proper path
    @PutMapping("/chat/{chatId}/message/{messageId}")
    fun updateMessage(@PathVariable chatId: UUID, @PathVariable messageId: UUID, @RequestBody req: UpdateMessageRequest): ResponseEntity<MessageResponse> {
        val updated = chatService.updateMessage(chatId, messageId, req.content, req.sender)
        return ResponseEntity.ok(MessageResponse.from(updated))
    }

    // Typo variant without the slash as requested
    @PutMapping("/chat/{chatId}message/{messageId}")
    fun updateMessageTypo(@PathVariable chatId: UUID, @PathVariable messageId: UUID, @RequestBody req: UpdateMessageRequest): ResponseEntity<MessageResponse> =
        updateMessage(chatId, messageId, req)
}

@ControllerAdvice
class ApiExceptionHandler {
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<Map<String, String>> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to (e.message ?: "Not found")))
}
