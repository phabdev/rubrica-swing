package it.phabdev.rubricaswing.swing;

import it.phabdev.rubricaswing.model.Person;
import it.phabdev.rubricaswing.utils.PersonEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Person> people;
    
    public MainFrame() {
        setTitle("Gestione Persone");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        people = new ArrayList<>();
        tableModel = new DefaultTableModel(new String[]{"Nome", "Cognome", "Telefono"}, 0);
        table = new JTable(tableModel);
        
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
        
        setVisible(true);
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
            tableModel.removeRow(selectedRow);
        }
    }
    
    public void addOrUpdatePerson(Person person, boolean isNew) {
        if (isNew) {
            people.add(person);
            tableModel.addRow(new Object[]{person.getNome(), person.getCognome(), person.getTelefono()});
        } else {
            int index = people.indexOf(person);
            tableModel.setValueAt(person.getNome(), index, 0);
            tableModel.setValueAt(person.getCognome(), index, 1);
            tableModel.setValueAt(person.getTelefono(), index, 2);
        }
    }
}