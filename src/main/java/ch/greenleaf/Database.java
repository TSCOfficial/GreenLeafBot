package ch.greenleaf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    // init database constants
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = Client.getConfig().get("DB_URL");
    private static final String USERNAME = Client.getConfig().get("DB_USER");
    private static final String PASSWORD = Client.getConfig().get("DB_PASSWORD"); //

    // init connection object
    private static Connection connection;
    // init properties object
    private static Properties properties;

    /**
     * Get the connection properties
     * @return The connection properties
     */
    private static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
        }
        return properties;
    }

    /**
     * Connect to the database
     * @return The database connection
     */
    public static Connection connect() {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e);
            }
        }
        return connection;
    }

    /**
     * Disconnect the current database connection
     */
    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }


}