package it.phabdev.rubricaswing.db;

import it.phabdev.rubricaswing.utils.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDatabaseManager implements DatabaseManager {
    private static Connection connection = null;

    public Connection getConnection() {
        if (connection == null) {
            try {
                String url = "jdbc:mysql://" +
                        ConfigManager.getProperty("DB_ADDRESS") + ":" +
                        ConfigManager.getProperty("DB_PORT") + "/" +
                        ConfigManager.getProperty("DB_NAME") +
                        "?useSSL=false&serverTimezone=UTC";

                String username = ConfigManager.getProperty("DB_USERNAME");
                String password = ConfigManager.getProperty("DB_PASSWORD");

                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connessione al database avvenuta con successo!");

            } catch (SQLException e) {
                System.err.println("Errore di connessione al database: " + e.getMessage());
            }
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connessione chiusa.");
            } catch (SQLException e) {
                System.err.println("Errore nella chiusura della connessione: " + e.getMessage());
            }
        }
    }
}
