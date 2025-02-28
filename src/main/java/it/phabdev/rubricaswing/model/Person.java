package it.phabdev.rubricaswing.model;

public class Person {
    private String nome, cognome, telefono;
    
    public Person(String nome, String cognome, String telefono) {
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
    }
    
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getTelefono() { return telefono; }
    
    public void setNome(String nome) { this.nome = nome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}