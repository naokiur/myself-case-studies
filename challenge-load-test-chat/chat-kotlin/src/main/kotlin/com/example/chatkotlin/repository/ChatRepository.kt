package com.example.chatkotlin.repository

import com.example.chatkotlin.domain.Chat
import org.springframework.data.repository.ListCrudRepository
import java.util.*

interface ChatRepository : ListCrudRepository<Chat, UUID>
