# Rubrica Swing

Rubrica Swing è un'applicazione Java che consente la gestione di una rubrica telefonica attraverso un'interfaccia grafica sviluppata con Swing. 
L'applicazione permette di aggiungere, modificare ed eliminare contatti, con la possibilità di salvare i dati su un file di testo o su un database MySQL, a seconda della configurazione scelta.

## Funzionalità

- **Visualizzazione dei contatti**: i contatti vengono mostrati in una tabella con colonne per Nome, Cognome, Indirizzo e Telefono.
- **Aggiunta di un nuovo contatto**: tramite il pulsante "Nuovo" è possibile inserire un nuovo contatto nella rubrica.
- **Modifica di un contatto esistente**: selezionando un contatto e cliccando su "Modifica" è possibile aggiornare le informazioni del contatto.
- **Eliminazione di un contatto**: selezionando un contatto e cliccando su "Elimina" è possibile rimuoverlo dalla rubrica.

## Configurazione

L'applicazione utilizza un file di configurazione `config.properties` per determinare il metodo di persistenza dei dati.
Se il file non esiste o è vuoto, l'applicazione utilizzerà di default il file `informazioni.txt` per la persistenza dei dati.
Se il file `config.properties` esiste ed è configurato correttamente, l'applicazione può utilizzare un database MySQL.

### File `config.properties`

Il file `config.properties` deve essere posizionato nella stessa directory del file JAR dell'applicazione e può contenere i seguenti parametri:

- `USE_DB`: flag per abilitare l'uso del database (valori possibili: `true` o `false`).
- `DB_URL`: URL di connessione al database MySQL.
- `DB_USER`: nome utente per la connessione al database.
- `DB_PASSWORD`: password per la connessione al database.

**Esempio di `config.properties` per l'uso del database**:

```
USE_DB=true
DB_URL=jdbc:mysql://localhost:3306/nome_database
DB_USER=nome_utente
DB_PASSWORD=password
```

**Esempio di `config.properties` per l'uso del file di testo**:

```
USE_DB=false
```

## Requisiti

- Java Development Kit (JDK) 8 o superiore.
- Per l'uso del database MySQL: un server MySQL funzionante e accessibile.

## Esecuzione dell'applicazione

1. Assicurarsi che il file `config.properties` sia configurato correttamente e posizionato nella stessa directory del file JAR dell'applicazione. (Se non dovesse esistere, creerà un file "informazioni.txt" senza collegarsi ad alcun database).
2. Eseguire il file JAR dell'applicazione con il comando (se il jar eseguibile si chiama "rubrica-swing.jar"):

   ```sh
   java -jar rubrica-swing.jar
   ```

## Note

- Se il file `config.properties` non esiste, è vuoto o il flag `USE_DB` è impostato su `false`, l'applicazione utilizzerà il file `informazioni.txt` per la persistenza dei dati.
- Se il flag `USE_DB` è impostato su `true`, ma i parametri di connessione al database sono mancanti o errati, l'applicazione mostrerà un messaggio di errore e non si avvierà.
- Assicurarsi che il database e la tabella siano configurati correttamente prima di utilizzare l'applicazione con la persistenza su database.
