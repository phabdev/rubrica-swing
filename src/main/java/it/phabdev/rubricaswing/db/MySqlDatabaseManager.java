package it.phabdev.rubricaswing.db;

import it.phabdev.rubricaswing.config.ConfigManager;
import it.phabdev.rubricaswing.model.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlDatabaseManager implements DatabaseManager {
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
        String sql = "CREATE TABLE IF NOT EXISTS contatti (id INT PRIMARY KEY AUTO_INCREMENT, nome VARCHAR(255), cognome VARCHAR(255), indirizzo VARCHAR(255), telefono VARCHAR(20), eta INT);";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella creazione della tabella", e);
        }
    }

    @Override
    public Integer saveContact(Contact contact) {
        String sql = "INSERT INTO contatti (nome, cognome, indirizzo, telefono, eta) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, contact.getNome());
            pstmt.setString(2, contact.getCognome());
            pstmt.setString(3, contact.getIndirizzo());
            pstmt.setString(4, contact.getTelefono());
            pstmt.setInt(5, contact.getEta());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Errore nel recupero dell'ID generato");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore nel salvataggio del contatto", e);
        }
    }

    @Override
    public void updateContact(Contact contact) {
        String sql = "UPDATE contatti SET nome=?, cognome=?, indirizzo=?, telefono=?, eta=? where id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, contact.getNome());
            pstmt.setString(2, contact.getCognome());
            pstmt.setString(3, contact.getIndirizzo());
            pstmt.setString(4, contact.getTelefono());
            pstmt.setInt(5, contact.getEta());
            pstmt.setInt(6, contact.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel salvataggio del contatto", e);
        }
    }

    @Override
    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT id, nome, cognome, indirizzo, telefono, eta FROM contatti";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Contact c = new Contact(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"), rs.getString("indirizzo"), rs.getString("telefono"), rs.getInt("eta"));
                contacts.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel recupero dei contatti", e);
        }
        return contacts;
    }

    @Override
    public void deleteContact(Integer id) {
        String sql = "DELETE FROM contatti WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
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
