import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBTest {
    private static Connection connection;
    private static Statement statement;

    @BeforeAll
    public static void setup() throws Exception {
        String jdbcURL = "jdbc:mysql://localhost:3306/testdb";
        String dbUsername = "root";
        String dbPassword = "";

        connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);
        statement = connection.createStatement();
    }

    @Test
    public void testUserDataRetrievalAndUpdate() throws Exception {
        String selectQuery = "SELECT * FROM Users WHERE UserID = 1002";
        ResultSet resultSet = statement.executeQuery(selectQuery);
        Assertions.assertTrue(resultSet.next(), "No user found with UserID = 1002");

        int userId = resultSet.getInt("UserID");
        String name = resultSet.getString("Name");
        String email = resultSet.getString("Email");
        Assertions.assertEquals(1002, userId, "UserID mismatch!");
        Assertions.assertEquals("Jane Smith", name, "Name mismatch!");
        Assertions.assertEquals("jane.smith@example.com", email, "Email mismatch!");

    }

    @AfterAll
    public static void teardown() throws Exception {
        if (statement != null) statement.close();
        if (connection != null) connection.close();
    }
}