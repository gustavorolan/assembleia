package com.sicredi.assembleia.rest;

import com.redis.testcontainers.RedisContainer;
import lombok.SneakyThrows;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.wait.strategy.Wait;
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

    public static GenericContainer<?> rabbitMQContainer = new RabbitMQContainer("rabbitmq:management")
            .withExposedPorts(5672)
            .waitingFor(Wait.forListeningPort());

    public static GenericContainer<?> redisContainer = new RedisContainer(
            RedisContainer.DEFAULT_IMAGE_NAME.withTag(RedisContainer.DEFAULT_TAG)
    ).withFileSystemBind("/path/on/host", "/data/on/container", BindMode.READ_WRITE)
            .waitingFor(Wait.forListeningPort());

    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("assembleia")
            .withUsername("postgres")
            .withPassword("123456")
            .withExposedPorts(5432)
            .waitingFor(Wait.forListeningPort());

    @Autowired
    PautaController pautaController;

    @Autowired
    SessaoVotacaoController sessaoVotacaoController;

    @Autowired
    VotoController votoController;

    @BeforeAll
    public static void init() {
        rabbitMQContainer.start();
        redisContainer.start();
        postgresContainer.start();

        int redisPort = redisContainer.getMappedPort(6379);
        int rabbitMQPort = rabbitMQContainer.getMappedPort(5672);
        int postgresPort = postgresContainer.getMappedPort(5432);

        System.setProperty("spring.rabbitmq.port", String.valueOf(rabbitMQPort));
        System.setProperty("spring.data.redis.port", String.valueOf(redisPort));
        System.setProperty("spring.datasource.url", String.format("jdbc:postgresql://localhost:%d/assembleia", postgresPort));
    }


    @SneakyThrows
    public void await(Integer ms) {
        Thread.sleep(ms);
    }
}