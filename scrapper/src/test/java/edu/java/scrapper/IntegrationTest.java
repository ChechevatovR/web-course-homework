package edu.java.scrapper;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class IntegrationTest {
    public static PostgreSQLContainer<?> POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("scrapper")
            .withUsername("postgres")
            .withPassword("postgres");
    }

    @BeforeAll
    static void beforeAll() {
        POSTGRES.start();
        runMigrations(POSTGRES);
    }

    @AfterAll
    static void afterAll() {
        POSTGRES.stop();
    }

    private static void runMigrations(JdbcDatabaseContainer<?> c) {
        try {
            Connection connection = DriverManager.getConnection(c.getJdbcUrl(), c.getUsername(), c.getPassword());
            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
            Liquibase liquibase = new Liquibase(
                "liquibase.xml",
                new DirectoryResourceAccessor(Path.of("..", "migrations")),
                database
            );
            liquibase.update(new Contexts());
        } catch (SQLException | LiquibaseException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }
}
