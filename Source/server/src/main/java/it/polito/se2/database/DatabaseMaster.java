package it.polito.se2.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseMaster {
	private static String url;
    private static String username;
    private static String password;
    
    public DatabaseMaster(String configFile) throws IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        FileInputStream in = new FileInputStream(configFile);
		properties.load(in); // load the file .properties
        
        String driver = properties.getProperty("jdbc.driver");
        url = properties.getProperty("jdbc.url");
        username = properties.getProperty("jdbc.username");
        if (username == null)
        	username = "";
        password = properties.getProperty("jdbc.password");
        if (password == null)
        	password = "";
        
        if (driver != null) 
            Class.forName(driver);
        
        //DriverManager.getConnection(url, username, password);
    }
    
    public static Connection getConnection() throws SQLException {
        //stabilisco la connessione tra l'applicazione Java e il database tramite "getConnection()" della classe "DriverManager"
        return (Connection) DriverManager.getConnection(url, username, password);
    }

}
