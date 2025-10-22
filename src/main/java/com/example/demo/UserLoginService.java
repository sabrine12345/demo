package com.example.securitydemo;

import java.sql.*;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;

public class UserLoginService {

    // ⚠️ Hardcoded database credentials (Security Risk)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/myapp";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password123";

    public boolean authenticate(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // ⚠️ Vulnerable to SQL Injection
        String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                // ⚠️ Weak encoding instead of proper password hashing
                String encoded = Base64.getEncoder().encodeToString(password.getBytes());
                logLogin(username, encoded);
                return true;
            }

        } catch (SQLException e) {
            // ⚠️ Logging sensitive information (may leak credentials)
            System.err.println("Database error: " + e.getMessage());
        }

        return false;
    }

    private void logLogin(String username, String passwordEncoded) {
        // ⚠️ Insecure logging: storing password info
        System.out.println("User " + username + " logged in with password (encoded): " + passwordEncoded);
    }
}
