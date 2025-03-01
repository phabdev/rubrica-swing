package it.phabdev.rubricaswing.db;

import java.sql.Connection;
import java.util.List;

public interface DatabaseManager {
    void connect();
    void createTable();
    void saveContact(String nome, String cognome, String telefono);
    List<String[]> getContacts();
    void deleteContact(String nome, String cognome);
    void close();
}
