import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.danga.MemCached.*;


public class LoginServlet extends HttpServlet {
    private Integer getUserID(String login, String password) throws SQLException {
        DatabaseClient databaseClient = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            databaseClient = DatabaseClient.getInstance();
            databaseClient.open();
            statement = databaseClient.prepareStatement("SELECT id FROM users WHERE login = ? AND password = ?");
            statement.setString(1, login == null ? "" : login);
            statement.setString(2, password == null ? "" : password);
            resultSet = statement.executeQuery();
            resultSet.beforeFirst();
            if (!resultSet.next()) {
                return null;
            } else {
                return resultSet.getInt(1);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (databaseClient != null) {
                databaseClient.close();
            }
        }
    }

    static Random random = new Random();

    private String generateTicket(int userId) {
        final char hexDigits[] = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 32; ++i) {
            stringBuilder.append(hexDigits[(random.nextInt() ^ userId ^ i) & 0xF]);
        }
        return stringBuilder.toString();
    }

    static {
      SockIOPool sockIOPool = SockIOPool.getInstance();
      sockIOPool.setServers(new String[] { "localhost:11211" });
      sockIOPool.initialize();
    }

    private void storeUserTicket(int userId, String ticket, String ip) {
        MemCachedClient cache = new MemCachedClient();
        cache.set(ticket, userId + " " + ip);
    }

    private String getClientIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        if (remoteAddr.equals("127.0.0.1")) {
            // proxy lokalne
            String xRealIp = request.getHeader("x-real-ip");
            if (xRealIp == null) {
               return "";
            }
            return xRealIp;
        } else {
            return remoteAddr;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printer = response.getWriter();
        try {
            Integer userId = getUserID(request.getParameter("login"), request.getParameter("password"));
            if (userId == null) {
                printer.println("BAD");
            } else {
                String ticket = generateTicket(userId);
                storeUserTicket(userId, ticket, getClientIp(request));
                printer.println("OK " + ticket);
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
            printer.println("ERROR");
        }
    }
}
