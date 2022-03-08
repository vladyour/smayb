package dev.vladyour.smayb

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
abstract class AbstractIntegrationTest {

    companion object {
        private val container = PostgreSQLContainer("postgres:13")
            .apply {
                withDatabaseName("postgres")
                withUsername("postgres")
                withPassword("supersecret")
                start()
            }


        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.password", container::getPassword)
            registry.add("spring.datasource.username", container::getUsername)
            registry.add("spring.flyway.url", container::getJdbcUrl)
            registry.add("spring.flyway.password", container::getPassword)
            registry.add("spring.flyway.user", container::getUsername)
        }
    }
}
