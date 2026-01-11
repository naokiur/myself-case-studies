package com.example.chatkotlin.chat

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
            createdAt = chatEntity.createdAt.toString(),
            updatedAt = chatEntity.updatedAt.toString(),
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
}
