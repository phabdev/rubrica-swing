package it.phabdev.rubricaswing.db;

import it.phabdev.rubricaswing.utils.ConfigManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class MySqlDatabaseManager implements DatabaseManager {
    private ConfigManager config;
    private Connection connection;

    public MySqlDatabaseManager(ConfigManager config) {
        this.config = config;
        if (config.isUseDb()) {
            connect();
            createTable();
        }
    }

    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection(config.getDbUrl(), config.getDbUser(), config.getDbPassword());
        } catch (SQLException e) {
            throw new RuntimeException("Errore di connessione al database", e);
        }
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS contatti (id INT PRIMARY KEY AUTO_INCREMENT, nome VARCHAR(255), cognome VARCHAR(255), telefono VARCHAR(20));";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella creazione della tabella", e);
        }
    }

    @Override
    public void saveContact(String nome, String cognome, String telefono) {
        String sql = "INSERT INTO contatti (nome, cognome, telefono) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, cognome);
            pstmt.setString(3, telefono);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel salvataggio del contatto", e);
        }
    }

    @Override
    public List<String[]> getContacts() {
        List<String[]> contacts = new ArrayList<>();
        String sql = "SELECT nome, cognome, telefono FROM contatti";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                contacts.add(new String[]{rs.getString("nome"), rs.getString("cognome"), rs.getString("telefono")});
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel recupero dei contatti", e);
        }
        return contacts;
    }

    @Override
    public void deleteContact(String nome, String cognome) {
        String sql = "DELETE FROM contatti WHERE nome = ? AND cognome = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, cognome);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore nell'eliminazione del contatto", e);
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
