import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {
    private boolean createUser(String login, String password) throws SQLException {
        PreparedStatement statement = null;
        DatabaseClient databaseClient = null;
        try {
            databaseClient = DatabaseClient.getInstance();
            databaseClient.open();
            statement = databaseClient.prepareStatement("INSERT IGNORE INTO users (login, password) VALUES (?, ?)");
            statement.setString(1, login);
            statement.setString(2, password);
            return statement.executeUpdate() > 0;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (databaseClient != null) {
                databaseClient.close();
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printer = response.getWriter();
        try {
            if (createUser(request.getParameter("login"), request.getParameter("password"))) {
               printer.println("OK");
            } else {
               printer.println("FAILED");
            }
        } catch (SQLException sqlException) {
            printer.println("ERROR");
        }
    }
}
