package ch.greenleaf.repository;

import ch.greenleaf.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage the Database requests for the Buttons.
 */
public class ButtonRepository {
    private static final Logger log = LoggerFactory.getLogger(ButtonRepository.class);

    public static List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT b FROM button_action b";

        try (
                Connection conn = Database.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                users.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            log.error("Error fetching tickets", e);
        }

        return users;
    }
}
