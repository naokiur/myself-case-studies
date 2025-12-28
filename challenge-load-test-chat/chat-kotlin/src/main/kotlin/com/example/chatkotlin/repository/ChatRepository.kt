package com.example.chatkotlin.repository

import com.example.chatkotlin.domain.Chat
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ChatRepository : JpaRepository<Chat, UUID>
