package it.phabdev.rubricaswing.utils;

import it.phabdev.rubricaswing.model.Person;
import it.phabdev.rubricaswing.swing.MainFrame;

import javax.swing.*;
import java.awt.*;

public class PersonEditor extends JDialog {
    private JTextField txtNome, txtCognome, txtTelefono;
    private Person person;
    private boolean isNew;
    
    public PersonEditor(MainFrame parent, Person person) {
        super(parent, "Editor Persona", true);
        this.person = person;
        this.isNew = (person == null);
        
        setLayout(new GridLayout(4, 2));
        add(new JLabel("Nome:"));
        txtNome = new JTextField(isNew ? "" : person.getNome());
        add(txtNome);
        add(new JLabel("Cognome:"));
        txtCognome = new JTextField(isNew ? "" : person.getCognome());
        add(txtCognome);
        add(new JLabel("Telefono:"));
        txtTelefono = new JTextField(isNew ? "" : person.getTelefono());
        add(txtTelefono);
        
        JButton btnSave = new JButton("Salva");
        JButton btnCancel = new JButton("Annulla");
        
        btnSave.addActionListener(e -> savePerson(parent));
        btnCancel.addActionListener(e -> dispose());
        
        add(btnSave);
        add(btnCancel);
        
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    private void savePerson(MainFrame parent) {
        if (isNew) {
            person = new Person(txtNome.getText(), txtCognome.getText(), txtTelefono.getText());
        } else {
            person.setNome(txtNome.getText());
            person.setCognome(txtCognome.getText());
            person.setTelefono(txtTelefono.getText());
        }
        parent.addOrUpdatePerson(person, isNew);
        dispose();
    }
}