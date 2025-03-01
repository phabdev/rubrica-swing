package it.phabdev.rubricaswing.model;

public class Contact {

    private Integer id;
    private String nome;
    private String cognome;
    private String indirizzo;
    private String telefono;
    private Integer eta;

    public Contact(String nome, String cognome, String indirizzo, String telefono, Integer eta){
        this(null,nome,cognome,indirizzo,telefono,eta);
    }
    public Contact(Integer id, String nome, String cognome, String indirizzo, String telefono, Integer eta){
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eta = eta;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }

    public Integer getEta() {
        return eta;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEta(Integer eta) {
        this.eta = eta;
    }
}
