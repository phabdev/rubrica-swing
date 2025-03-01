package it.phabdev.rubricaswing.config;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
public class ConfigManager {
    private static final String CONFIG_FILE = "config.properties";
    private static final String DEFAULT_FILE = "informazioni.txt";
    
    private boolean useDb;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    
    public ConfigManager() {
        loadConfig();
    }
    
    private void loadConfig() {
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists() || configFile.length() == 0) {
            useDb = false;
            return;
        }
        
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            Properties props = new Properties();
            props.load(fis);
            
            useDb = Boolean.parseBoolean(props.getProperty("USE_DB", "false"));
            if (useDb) {
                dbUrl = props.getProperty("DB_URL");
                dbUser = props.getProperty("DB_USER");
                dbPassword = props.getProperty("DB_PASSWORD");
                
                if (dbUrl == null || dbUser == null || dbPassword == null) {
                    throw new IllegalArgumentException("Parametri di connessione mancanti! Aggiungere DB_URL, DB_USER e DB_PASSWORD in config.properties.");
                }
            }
        } catch (IOException e) {
            useDb = false;
        }
    }
    
    public boolean isUseDb() {
        return useDb;
    }
    
    public String getDbUrl() {
        return dbUrl;
    }
    
    public String getDbUser() {
        return dbUser;
    }
    
    public String getDbPassword() {
        return dbPassword;
    }
}