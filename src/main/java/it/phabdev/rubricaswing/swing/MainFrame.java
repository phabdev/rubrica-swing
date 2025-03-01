package it.phabdev.rubricaswing.swing;

import it.phabdev.rubricaswing.config.ConfigManager;
import it.phabdev.rubricaswing.model.Contact;
import it.phabdev.rubricaswing.db.DatabaseManager;
import it.phabdev.rubricaswing.db.MySqlDatabaseManager;
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
    private List<Contact> people;
    private DatabaseManager dbManager;
    private boolean useDb;

    public MainFrame() {
        setTitle("Rubrica Swing");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            ConfigManager configManager = new ConfigManager();
            useDb = configManager.isUseDb();

            if (useDb) {
                dbManager = new MySqlDatabaseManager(configManager);
                people = dbManager.getContacts();
            } else {
                people = FileManager.loadPersons();
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore imprevisto: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        tableModel = new DefaultTableModel(new String[]{"Nome", "Cognome", "Indirizzo", "Telefono"}, 0) {
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

        if (!useDb) {
            /*
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    FileManager.savePersons(people);
                }
            });
            */
        }

        setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        //if (useDb) {
        for (Contact person : people) {
            tableModel.addRow(new Object[]{person.getNome(), person.getCognome(), person.getIndirizzo(), person.getTelefono()});
        }
        //} else {
        //  for (Contact person : people) {
        //    tableModel.addRow(new Object[]{person.getNome(), person.getCognome(), person.getIndirizzo(), person.getTelefono()});
        //}
        //}
    }

    private void openEditor(Contact person) {
        new PersonEditor(this, person);
    }

    private void editPerson() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona una persona da modificare.");
            return;
        }
        Contact person = people.get(selectedRow);
        openEditor(person);
    }

    private void deletePerson() {
        int selectedRow = table.getSelectedRow();
        Contact person = people.get(selectedRow);
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona una persona da eliminare.");
            return;
        }
        if (useDb) {
            String nome = (String) tableModel.getValueAt(selectedRow, 0);
            String cognome = (String) tableModel.getValueAt(selectedRow, 1);
            int confirm = JOptionPane.showConfirmDialog(this, "Eliminare la persona " + nome + " " + cognome + "?", "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                people.remove(selectedRow);
                dbManager.deleteContact(person.getId());
                refreshTable();
            }
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Eliminare la persona " + person.getNome() + " " + person.getCognome() + "?", "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                people.remove(selectedRow);
                FileManager.savePersons(people);
                refreshTable();
            }
        }
    }

    public void addOrUpdatePerson(Contact person, boolean isNew) {
        if (useDb) {
            if (isNew) {
                Integer id = dbManager.saveContact(person);
                person.setId(id);
                people.add(person);
            } else {
                dbManager.updateContact(person);
            }
        } else {
            if (isNew) {
                people.add(person);
            }
            FileManager.savePersons(people);
        }
        refreshTable();
    }
}
