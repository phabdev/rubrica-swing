package it.phabdev.rubricaswing.swing;

import it.phabdev.rubricaswing.model.Contact;

import javax.swing.*;
import java.awt.*;

public class PersonEditor extends JDialog {
    private JTextField txtNome, txtCognome, txtIndirizzo, txtTelefono, txtEta;
    private Contact person;
    private boolean isNew;

    public PersonEditor(MainFrame parent, Contact person) {
        super(parent, "Editor Persona", true);
        this.person = person;
        this.isNew = (person == null);

        setLayout(new GridLayout(6, 2));
        add(new JLabel("Nome:"));
        txtNome = new JTextField(isNew ? "" : person.getNome());
        add(txtNome);
        add(new JLabel("Cognome:"));
        txtCognome = new JTextField(isNew ? "" : person.getCognome());
        add(txtCognome);
        add(new JLabel("Indirizzo:"));
        txtIndirizzo = new JTextField(isNew ? "" : person.getIndirizzo());
        add(txtIndirizzo);
        add(new JLabel("Telefono:"));
        txtTelefono = new JTextField(isNew ? "" : person.getTelefono());
        add(txtTelefono);
        add(new JLabel("Età:"));
        txtEta = new JTextField(isNew ? "" : String.valueOf(person.getEta()));
        add(txtEta);

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
        try {
            int eta = Integer.parseInt(txtEta.getText().trim());
            if (isNew) {
                person = new Contact(txtNome.getText(), txtCognome.getText(), txtIndirizzo.getText(), txtTelefono.getText(), eta);
            } else {
                person.setNome(txtNome.getText());
                person.setCognome(txtCognome.getText());
                person.setIndirizzo(txtIndirizzo.getText());
                person.setTelefono(txtTelefono.getText());
                person.setEta(eta);
            }
            parent.addOrUpdatePerson(person, isNew);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Inserisci un valore numerico valido per l'età.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}
