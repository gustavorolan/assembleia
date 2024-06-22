package com.sicredi.assembleia.rest;

import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("teste")
@TestExecutionListeners(
        value = FlywayTestExecutionListener.class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
@FlywayTest
abstract class BaseTest {

    @Container
    public static GenericContainer<?> rabbitMQContainer = new GenericContainer<>("rabbitmq:management")
            .withExposedPorts(5672)
            .withNetworkAliases("rabbit")
            .waitingFor(Wait.forListeningPort());

    @Container
    public static GenericContainer<?> redisContainer = new GenericContainer<>("redis:latest")
            .withExposedPorts(6379)
            .withNetworkAliases("redis")
            .waitingFor(Wait.forListeningPort());

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("assembleia")
            .withUsername("postgres")
            .withPassword("123456")
            .withExposedPorts(5432)
            .withNetworkAliases("postgres")
            .waitingFor(Wait.forListeningPort());

    @BeforeAll
    public static void init() {
        rabbitMQContainer.start();
        int rabbitMQPort = rabbitMQContainer.getMappedPort(5672);

        redisContainer.start();
        int redisPort = redisContainer.getMappedPort(6379);

        postgresContainer.start();
        int postgresPort = postgresContainer.getMappedPort(5432);

        System.setProperty("rabbitmq.port", String.valueOf(rabbitMQPort));
        System.setProperty("redis.port", String.valueOf(redisPort));
        System.setProperty("spring.datasource.url", String.format("jdbc:postgresql://localhost:%d/assembleia", postgresPort));
    }
}