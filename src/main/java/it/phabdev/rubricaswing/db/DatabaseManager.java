package it.phabdev.rubricaswing.db;

import it.phabdev.rubricaswing.model.Contact;

import java.util.List;

public interface DatabaseManager {
    void connect();
    void createTable();
    Integer saveContact(Contact contact);
    void updateContact(Contact contact);
    List<Contact> getContacts();

    //void deleteContacts(String nome, String cognome);
    void deleteContact(Integer id);
    void close();
}
