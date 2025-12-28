package com.example.chatkotlin.chat

import org.springframework.data.repository.ListCrudRepository
import java.util.*

interface ChatRepository : ListCrudRepository<ChatEntity, UUID>
