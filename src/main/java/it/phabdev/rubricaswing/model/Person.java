package it.phabdev.rubricaswing.model;

public class Person {
    private String nome;
    private String cognome;
    private String indirizzo;
    private String telefono;
    private int eta;

    public Person(String nome, String cognome, String indirizzo, String telefono, int eta) {
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eta = eta;
    }

    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getIndirizzo() { return indirizzo; }
    public String getTelefono() { return telefono; }
    public int getEta() { return eta; }

    public void setNome(String nome) { this.nome = nome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEta(int eta) { this.eta = eta; }
}
