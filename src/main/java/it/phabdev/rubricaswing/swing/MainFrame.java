package it.phabdev.rubricaswing.swing;

import it.phabdev.rubricaswing.model.Person;
import it.phabdev.rubricaswing.utils.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MainFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Person> people;

    public MainFrame() {
        setTitle("Rubrica Swing");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Carica le persone dal file
        people = FileManager.loadPersons();
        tableModel = new DefaultTableModel(new String[]{"Nome", "Cognome", "Indirizzo", "Telefono"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        refreshTable();

        JPanel buttonPanel = new JPanel();
        JButton btnNew = new JButton("Nuovo");
        JButton btnEdit = new JButton("Modifica");
        JButton btnDelete = new JButton("Elimina");

        btnNew.addActionListener(e -> openEditor(null));
        btnEdit.addActionListener(e -> editPerson());
        btnDelete.addActionListener(e -> deletePerson());

        buttonPanel.add(btnNew);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Salvataggio automatico alla chiusura
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                FileManager.savePersons(people);
            }
        });

        setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Person person : people) {
            tableModel.addRow(new Object[]{person.getNome(), person.getCognome(), person.getIndirizzo(), person.getTelefono()});
        }
    }

    private void openEditor(Person person) {
        new PersonEditor(this, person);
    }

    private void editPerson() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona una persona da modificare.");
            return;
        }
        Person person = people.get(selectedRow);
        openEditor(person);
    }

    private void deletePerson() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona una persona da eliminare.");
            return;
        }
        Person person = people.get(selectedRow);
        int confirm = JOptionPane.showConfirmDialog(this, "Eliminare la persona " + person.getNome() + " " + person.getCognome() + "?", "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            people.remove(selectedRow);
            refreshTable();
        }
    }

    public void addOrUpdatePerson(Person person, boolean isNew) {
        if (isNew) {
            people.add(person);
        }
        refreshTable();
    }
}
