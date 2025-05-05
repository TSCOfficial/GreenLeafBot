package ch.greenleaf.repository;

import ch.greenleaf.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage the Database requests for the Ticket System.
 */
public class TicketRepository {
    private static final Logger log = LoggerFactory.getLogger(TicketRepository.class);

    public static void addTicket(String username, String email) {
        String sql = "INSERT INTO tickets (username, email) VALUES (?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error inserting ticket", e);
        }
    }

    public static List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT username FROM tickets";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            log.error("Error fetching tickets", e);
        }

        return users;
    }
}
