import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


final class DatabaseClient {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/javatest?autoReconnect=true";
    private static final String USER_NAME = "polchawa";
    private static final String PASSWORD = "bzium";


    private DatabaseClient() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    Connection connection = null;


    public void open() throws SQLException {
        close();
        connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
    }


    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }


    public PreparedStatement prepareStatement(String stmt) throws SQLException {
        return connection.prepareStatement(stmt);
    }


    private static DatabaseClient instance = new DatabaseClient();

    public static DatabaseClient getInstance() {
        return instance;
    }
}
