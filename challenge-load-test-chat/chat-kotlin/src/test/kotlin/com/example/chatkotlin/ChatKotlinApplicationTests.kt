package com.example.chatkotlin

import com.example.chatkotlin.chat.ChatRepository
import com.example.chatkotlin.message.MessageRepository
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = [
        // Avoid requiring a real DataSource or JDBC repositories during context load tests
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration"
    ]
)
@Import(ChatKotlinApplicationTests.TestConfig::class)
class ChatKotlinApplicationTests {

    @TestConfiguration
    class TestConfig {
        @Bean
        fun chatRepository(): ChatRepository = mock()

        @Bean
        fun messageRepository(): MessageRepository = mock()
    }

    @Test
    fun contextLoads() {
        // Verifies Spring context starts
    }
}
