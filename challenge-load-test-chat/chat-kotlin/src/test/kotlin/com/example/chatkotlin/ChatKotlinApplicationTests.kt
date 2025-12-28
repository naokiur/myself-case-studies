package com.example.chatkotlin

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import com.example.chatkotlin.chat.ChatRepository
import com.example.chatkotlin.message.MessageRepository

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = [
        // Avoid requiring a real DataSource or JDBC repositories during context load tests
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration"
    ]
)
class ChatKotlinApplicationTests {

    // Provide required beans so that @Service beans can be created
    @MockBean
    lateinit var chatRepository: ChatRepository

    @MockBean
    lateinit var messageRepository: MessageRepository

    @Test
    fun contextLoads() {
        // Verifies Spring context starts
    }
}
