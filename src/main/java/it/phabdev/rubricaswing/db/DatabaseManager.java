package it.phabdev.rubricaswing.db;

import java.sql.Connection;

public interface DatabaseManager {
    public Connection getConnection();
    public void closeConnection();
}
