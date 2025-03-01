package it.phabdev.rubricaswing.utils;

import it.phabdev.rubricaswing.model.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {
    private static final String FILE_NAME = "informazioni.txt";

    // Metodo per leggere le persone dal file
    public static List<Contact> loadPersons() {
        List<Contact> people = new ArrayList<>();

        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("⚠️ Il file " + FILE_NAME + " non esiste. Creazione di un nuovo file.");
            return people;
        }
        /*
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) {
                    String nome = parts[0];
                    String cognome = parts[1];
                    String indirizzo = parts[2];
                    String telefono = parts[3];
                    int eta = Integer.parseInt(parts[4]);
                    people.add(new Person(nome, cognome, indirizzo, telefono, eta));
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file: " + e.getMessage());
        }*/
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                if (parts.length == 5) {
                    String nome = parts[0];
                    String cognome = parts[1];
                    String indirizzo = parts[2];
                    String telefono = parts[3];
                    int eta = Integer.parseInt(parts[4]);
                    people.add(new Contact(nome, cognome, indirizzo, telefono, eta));
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file: " + e.getMessage());
        }

        return people;
    }

    // Metodo per salvare le persone nel file
    public static void savePersons(List<Contact> people) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Contact person : people) {
                String line = person.getNome() + ";" +
                              person.getCognome() + ";" +
                              person.getIndirizzo() + ";" +
                              person.getTelefono() + ";" +
                              person.getEta();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Errore nella scrittura del file: " + e.getMessage());
        }
    }
}
