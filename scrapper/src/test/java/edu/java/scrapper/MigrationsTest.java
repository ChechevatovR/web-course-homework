package edu.java.scrapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MigrationsTest extends IntegrationTest {
    @Test
    public void testRunMigrations() throws SQLException, DatabaseException {
        Connection connection = DriverManager.getConnection(
            POSTGRES.getJdbcUrl(),
            POSTGRES.getUsername(),
            POSTGRES.getPassword()
        );
        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(
            "SELECT count(*) FROM information_schema.tables WHERE table_schema = 'public'"
        );
        Assertions.assertTrue(resultSet.next());
        int tablesInDb = resultSet.getInt(1);
        Assertions.assertEquals(5, tablesInDb);
    }
}
